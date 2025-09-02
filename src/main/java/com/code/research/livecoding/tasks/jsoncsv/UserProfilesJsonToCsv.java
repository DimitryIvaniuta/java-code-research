package com.code.research.livecoding.tasks.jsoncsv;

import com.fasterxml.jackson.core.JsonFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * User profiles JSON -> CSV (union header, flattened).
 * <p>
 * - Input: JSON array of objects (user profiles). Nested objects are flattened with dot paths
 * (e.g., address.city). Arrays of scalars are joined by ';'. Arrays of objects are NOT supported.
 * - Header: union of all (flattened) keys across rows, sorted lexicographically (stable).
 * - Memory: streaming, two-pass; spools input to a temp file; per-row memory only.
 * - Output: RFC4180-like CSV quoting; missing keys -> empty cells.
 * <p>
 * Dependencies: com.fasterxml.jackson.core:jackson-core
 */
public final class UserProfilesJsonToCsv {

    private static final JsonFactory JSON = new JsonFactory();
    private static final char DELIM = ',';

    /**
     * Convert profiles JSON to CSV.
     * Streams are NOT closed by this method.
     */
    public static void jsonProfilesToCsv(InputStream in, OutputStream out) throws IOException {
        // Spool to temp file to allow two streaming passes without loading all bytes into RAM
        Path tmp = Files.createTempFile("profiles-", ".json");
        try {
            Files.copy(in, tmp, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // PASS 1: collect union of flattened keys
            SortedSet<String> headerSet = new TreeSet<>();
            try (InputStream p1 = Files.newInputStream(tmp);
                 JsonParser jp = JSON.createParser(p1)) {

                require(next(jp) == JsonToken.START_ARRAY, "Expected JSON array");
                while (next(jp) != JsonToken.END_ARRAY) {
                    require(jp.currentToken() == JsonToken.START_OBJECT, "Expected object");
                    collectKeysFlat(jp, null, headerSet); // consumes the object
                }
            }
            if (headerSet.isEmpty()) return;

            List<String> headers = new ArrayList<>(headerSet);
            Map<String, Integer> colIndex = new HashMap<>(headers.size() * 2);
            for (int i = 0; i < headers.size(); i++) colIndex.put(headers.get(i), i);

            // PASS 2: write CSV with stable header order
            try (InputStream p2 = Files.newInputStream(tmp);
                 JsonParser jp = JSON.createParser(p2);
                 Writer w = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))) {

                writeRow(w, headers);

                require(next(jp) == JsonToken.START_ARRAY, "Expected JSON array");
                String[] row = new String[headers.size()];
                while (next(jp) != JsonToken.END_ARRAY) {
                    Arrays.fill(row, "");
                    require(jp.currentToken() == JsonToken.START_OBJECT, "Expected object");
                    fillRowFlat(jp, null, row, colIndex); // consumes the object
                    writeRow(w, Arrays.asList(row));
                }
                w.flush();
            }
        } finally {
            try {
                Files.deleteIfExists(tmp);
            } catch (IOException ignore) {
            }
        }
    }

    // ---------- PASS 1: header collection (flattening) ----------

    private static void collectKeysFlat(JsonParser jp, String prefix, Set<String> header) throws IOException {
        // jp is at START_OBJECT
        while (next(jp) != JsonToken.END_OBJECT) {
            require(jp.currentToken() == JsonToken.FIELD_NAME, "Expected field name");
            String name = jp.getText();
            String path = (prefix == null || prefix.isEmpty()) ? name : prefix + "." + name;

            JsonToken v = next(jp);
            if (isScalar(v)) {
                header.add(path);
            } else if (v == JsonToken.START_OBJECT) {
                collectKeysFlat(jp, path, header); // recurse; consumes END_OBJECT
            } else if (v == JsonToken.START_ARRAY) {
                // Allow arrays of scalars -> single column; reject arrays of objects
                JsonToken t = next(jp); // first element or END_ARRAY
                if (t == JsonToken.END_ARRAY) {
                    header.add(path); // empty array -> empty cell
                } else if (isScalar(t)) {
                    header.add(path);
                    // consume the rest of the array
                    while (next(jp) != JsonToken.END_ARRAY) {
                        require(isScalar(jp.currentToken()), "Arrays of objects/arrays are not supported");
                    }
                } else {
                    throw new IllegalArgumentException("Arrays of objects are not supported at: " + path);
                }
            } else {
                throw new IllegalArgumentException("Unsupported token for key " + path + ": " + v);
            }
        }
    }

    // ---------- PASS 2: row filling (flattening) ----------

    private static void fillRowFlat(JsonParser jp, String prefix, String[] row, Map<String, Integer> colIndex) throws IOException {
        // jp is at START_OBJECT
        while (next(jp) != JsonToken.END_OBJECT) {
            require(jp.currentToken() == JsonToken.FIELD_NAME, "Expected field name");
            String name = jp.getText();
            String path = (prefix == null || prefix.isEmpty()) ? name : prefix + "." + name;

            JsonToken v = next(jp);
            if (isScalar(v)) {
                put(row, colIndex, path, scalarAsText(jp, v));
            } else if (v == JsonToken.START_OBJECT) {
                fillRowFlat(jp, path, row, colIndex);
            } else if (v == JsonToken.START_ARRAY) {
                // Join arrays of scalars with ';'
                String joined = readArrayOfScalarsAsJoined(jp, path, ';');
                put(row, colIndex, path, joined);
            } else {
                throw new IllegalArgumentException("Unsupported token for " + path + ": " + v);
            }
        }
    }

    private static String readArrayOfScalarsAsJoined(JsonParser jp, String path, char sep) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        JsonToken t = next(jp);
        if (t == JsonToken.END_ARRAY) return ""; // empty array
        while (true) {
            require(isScalar(t), "Arrays of objects/arrays are not supported at " + path);
            String piece = scalarAsText(jp, t);
            if (!first) sb.append(sep);
            sb.append(piece == null ? "" : piece);
            t = next(jp);
            if (t == JsonToken.END_ARRAY) break;
            first = false;
        }
        return sb.toString();
    }

    // ---------- CSV + small helpers ----------

    private static void put(String[] row, Map<String, Integer> idx, String key, String val) {
        Integer i = idx.get(key);
        if (i != null) row[i] = (val == null ? "" : val);
    }

    private static JsonToken next(JsonParser jp) throws IOException {
        return jp.nextToken();
    }

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
        return jp.getText(); // preserves original representation
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
        boolean needs = s.indexOf(DELIM) >= 0 || s.indexOf('"') >= 0 || s.indexOf('\n') >= 0 || s.indexOf('\r') >= 0;
        return needs ? "\"" + s.replace("\"", "\"\"") + "\"" : s;
    }

    private static void require(boolean cond, String msg) {
        if (!cond) throw new IllegalArgumentException(msg);
    }

    public static void main(String[] args) throws Exception {
        String json = """
                [
                  {
                    "name":"Alice",
                    "email":"alice@example.com",
                    "address":{"city":"Warsaw","zip":"00-001"},
                    "tags":["pro","beta"],
                    "age":31
                  },
                  {
                    "name":"Bob",
                    "address":{"city":"Gdańsk"},
                    "phone":"+48-111-222-333",
                    "tags":[]
                  },
                  {
                    "name":"Carol",
                    "email":"carol@example.com",
                    "address":{"city":"Kraków","street":"Floriańska 1"},
                    "newsletter":true
                  }
                ]
                """;

        UserProfilesJsonToCsv.jsonProfilesToCsv(
                new java.io.ByteArrayInputStream(json.getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                System.out
        );
    }

}
