package com.code.research.algorithm.tasks.jsoncsv;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
public class JsonCsvJacksonStreaming {

    private static final char DELIM = ',';
    private static final JsonFactory JSON = new JsonFactory();

    public static void jsonArrayToCsv(InputStream in, OutputStream out) throws IOException {
        // --- Spool to temp file (keeps memory low; works for any InputStream) ---
        Path tmp = Files.createTempFile("json2csv-", ".json");
        try {
            Files.copy(in, tmp, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // --- PASS 1: collect union of keys ---
            SortedSet<String> headerSet = new TreeSet<>();
            try (InputStream p1 = Files.newInputStream(tmp);
                 JsonParser jp = JSON.createParser(p1)) {

                require(jp.nextToken() == JsonToken.START_ARRAY, "Expected JSON array");
                while (jp.nextToken() != JsonToken.END_ARRAY) {
                    require(jp.currentToken() == JsonToken.START_OBJECT, "Expected object");
                    while (jp.nextToken() != JsonToken.END_OBJECT) {
                        require(jp.currentToken() == JsonToken.FIELD_NAME, "Expected field name");
                        String field = jp.getText();
                        JsonToken vtok = jp.nextToken();
                        require(isScalar(vtok), "Nested structures not supported (flat only)");
                        headerSet.add(field);
                    }
                }
            }
            if (headerSet.isEmpty()) return;

            List<String> headers = new ArrayList<>(headerSet);
            Map<String, Integer> colIndex = new HashMap<>(headers.size() * 2);
            for (int i = 0; i < headers.size(); i++) colIndex.put(headers.get(i), i);

            // --- PASS 2: write CSV with stable header order ---
            try (InputStream p2 = Files.newInputStream(tmp);
                 JsonParser jp = JSON.createParser(p2);
                 Writer w = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))) {

                // header
                writeRow(w, headers);

                require(jp.nextToken() == JsonToken.START_ARRAY, "Expected JSON array");
                String[] row = new String[headers.size()];
                while (jp.nextToken() != JsonToken.END_ARRAY) {
                    Arrays.fill(row, ""); // default empty cells
                    require(jp.currentToken() == JsonToken.START_OBJECT, "Expected object");
                    while (jp.nextToken() != JsonToken.END_OBJECT) {
                        require(jp.currentToken() == JsonToken.FIELD_NAME, "Expected field name");
                        String field = jp.getText();
                        JsonToken vtok = jp.nextToken();
                        require(isScalar(vtok), "Nested structures not supported (flat only)");
                        Integer idx = colIndex.get(field);
                        if (idx != null) row[idx] = scalarAsText(jp, vtok);
                        // unknown fields can't happen (header is union of all keys)
                    }
                    writeRow(w, Arrays.asList(row));
                }
                w.flush();
            }
        } finally {
            try { Files.deleteIfExists(tmp); } catch (IOException ignore) {}
        }
    }

    // --- helpers ---

    private static boolean isScalar(JsonToken t) {
        return t == JsonToken.VALUE_STRING
                || t == JsonToken.VALUE_NUMBER_INT
                || t == JsonToken.VALUE_NUMBER_FLOAT
                || t == JsonToken.VALUE_TRUE
                || t == JsonToken.VALUE_FALSE
                || t == JsonToken.VALUE_NULL;
    }

    private static String scalarAsText(JsonParser jp, JsonToken t) throws IOException {
        if (t == JsonToken.VALUE_NULL) return "";
        // Use parser text to preserve original number/boolean/string representation
        return jp.getText();
    }

    private static void writeRow(Writer w, List<String> cols) throws IOException {
        for (int i = 0; i < cols.size(); i++) {
            if (i > 0) w.write(DELIM);
            w.write(csvEscape(cols.get(i)));
        }
        w.write('\n');
    }

    private static String csvEscape(String s) {
        if (s == null) return "";
        boolean needsQuote = s.indexOf(DELIM) >= 0 || s.indexOf('"') >= 0 || s.indexOf('\n') >= 0 || s.indexOf('\r') >= 0;
        return needsQuote ? "\"" + s.replace("\"", "\"\"") + "\"" : s;
    }

    private static void require(boolean cond, String msg) {
        if (!cond) throw new IllegalArgumentException(msg);
    }

    public static void main(String[] args) throws Exception {
        String json = """
        [
          {"b":2,"a":"x"},
          {"a":"y","c":true,"n":null},
          {"a":"with,comma","b":"with \\\"quote\\\""},
          {"a":"arow","b":"brow", "c": "crow","n":"nr\\\"ow"}
        ]
        """;
        jsonArrayToCsv(
                new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)),
                System.out
        );
    }
}
