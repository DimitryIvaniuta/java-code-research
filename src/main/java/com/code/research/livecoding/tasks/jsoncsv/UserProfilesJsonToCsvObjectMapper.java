package com.code.research.livecoding.tasks.jsoncsv;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public final class UserProfilesJsonToCsvObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper(new JsonFactory());
    private static final char DELIM = ',';

    /**
     * Convert a JSON array of user profiles to CSV (union header, deterministic order).
     * Streams are NOT closed by this method.
     */
    public static void jsonProfilesToCsv(InputStream in, OutputStream out) throws IOException {
        // Spool once so we can do two streaming passes without loading everything into memory
        Path tmp = Files.createTempFile("profiles-", ".json");
        try {
            Files.copy(in, tmp, StandardCopyOption.REPLACE_EXISTING);

            // PASS 1: collect union of flattened keys
// --- PASS 1: collect union of flattened keys ---
            SortedSet<String> headerSet = new TreeSet<>();
            try (InputStream p1 = Files.newInputStream(tmp);
                 JsonParser jp = MAPPER.getFactory().createParser(p1)) {

                if (jp.nextToken() != com.fasterxml.jackson.core.JsonToken.START_ARRAY) {
                    throw new IllegalArgumentException("Expected JSON array");
                }

                // Move to first element and iterate elements
                while (jp.nextToken() != com.fasterxml.jackson.core.JsonToken.END_ARRAY) {
                    JsonNode node = MAPPER.readTree(jp); // reads one array element
                    if (node == null || !node.isObject()) {
                        throw new IllegalArgumentException("Array elements must be objects");
                    }
                    Map<String, String> flat = new LinkedHashMap<>();
                    flatten(node, "", flat);
                    headerSet.addAll(flat.keySet());
                }
            }
            if (headerSet.isEmpty()) return;

            List<String> headers = new ArrayList<>(headerSet);
            Map<String, Integer> colIndex = new HashMap<>(headers.size() * 2);
            for (int i = 0; i < headers.size(); i++) colIndex.put(headers.get(i), i);

            // PASS 2: write CSV
            try (InputStream p2 = Files.newInputStream(tmp);
                 JsonParser jp = MAPPER.getFactory().createParser(p2);
                 Writer w = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))) {

                writeRow(w, headers);

                if (jp.nextToken() != com.fasterxml.jackson.core.JsonToken.START_ARRAY) {
                    throw new IllegalArgumentException("Expected JSON array");
                }

                String[] row = new String[headers.size()];
                while (jp.nextToken() != com.fasterxml.jackson.core.JsonToken.END_ARRAY) {
                    JsonNode node = MAPPER.readTree(jp); // one element
                    if (node == null || !node.isObject()) {
                        throw new IllegalArgumentException("Array elements must be objects");
                    }

                    Arrays.fill(row, "");
                    Map<String, String> flat = new LinkedHashMap<>();
                    flatten(node, "", flat);

                    for (Map.Entry<String, String> e : flat.entrySet()) {
                        Integer idx = colIndex.get(e.getKey());
                        if (idx != null) row[idx] = e.getValue() == null ? "" : e.getValue();
                    }
                    writeRow(w, Arrays.asList(row));
                }
                w.flush();
            }
        } finally {
            try { Files.deleteIfExists(tmp); } catch (IOException ignore) {}
        }
    }

    // ---- flatten helpers (object -> dot paths; arrays of scalars -> ';'-joined) ----

    private static void flatten(JsonNode node, String prefix, Map<String, String> out) {
        if (node == null || node.isNull()) { // null for an object field -> empty cell
            if (!prefix.isEmpty()) out.put(prefix, "");
            return;
        }
        if (node.isObject()) {
            node.fields().forEachRemaining(e -> {
                String path = prefix.isEmpty() ? e.getKey() : prefix + "." + e.getKey();
                flatten(e.getValue(), path, out);
            });
            return;
        }
        if (node.isArray()) {
            if (!prefix.isEmpty()) out.put(prefix, joinArray(node));
            else throw new IllegalArgumentException("Top-level arrays inside element are unsupported");
            return;
        }
        // scalar
        if (!prefix.isEmpty()) out.put(prefix, node.asText());
    }

    private static String joinArray(JsonNode arr) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (JsonNode n : arr) {
            if (n.isObject() || n.isArray())
                throw new IllegalArgumentException("Arrays of objects/arrays are not supported");
            if (!first) sb.append(';');
            sb.append(n.isNull() ? "" : n.asText());
            first = false;
        }
        return sb.toString();
    }

    // ---- CSV helpers ----

    private static void writeRow(Writer w, List<String> cols) throws IOException {
        for (int i = 0; i < cols.size(); i++) {
            if (i > 0) w.write(DELIM);
            w.write(csvEscape(cols.get(i)));
        }
        w.write('\n');
    }

    private static String csvEscape(String s) {
        if (s == null) return "";
        boolean needs = s.indexOf(DELIM) >= 0 || s.indexOf('"') >= 0 || s.indexOf('\n') >= 0 || s.indexOf('\r') >= 0;
        return needs ? "\"" + s.replace("\"", "\"\"") + "\"" : s;
    }

    public static void main(String[] args) throws Exception {
        String json = """
      [
        {"name":"Alice","email":"alice@example.com","address":{"city":"Warsaw","zip":"00-001"},"tags":["pro","beta"],"age":31},
        {"name":"Bob","address":{"city":"Gdańsk"},"phone":"+48-111-222-333","tags":[]},
        {"name":"Carol","email":"carol@example.com","address":{"city":"Kraków","street":"Floriańska 1"},"newsletter":true}
      ]
      """;
        UserProfilesJsonToCsvObjectMapper.jsonProfilesToCsv(
                new java.io.ByteArrayInputStream(json.getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                System.out
        );
    }

}
