package com.code.research.livecoding.tasks.jsoncsv;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class JsonCsv {

    private static final char DELIM = ',';
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Convert a flat JSON array of objects to CSV.
     * - Header: lexicographically sorted union of keys.
     * - Missing keys -> empty field.
     * - Flat objects only (no nested objects/arrays).
     * Streams are NOT closed by this method.
     */
    public static void jsonArrayToCsv(InputStream in, OutputStream out) throws IOException {
        List<Map<String, Object>> rows = MAPPER.readValue(in, new TypeReference<List<Map<String, Object>>>() {});
        if (rows == null || rows.isEmpty()) return;

        // 1) Build lexicographically-sorted header (stable across runs)
        SortedSet<String> headerSet = new TreeSet<>();
        for (Map<String, Object> row : rows) {
            if (row != null) headerSet.addAll(row.keySet());
        }
        if (headerSet.isEmpty()) return;

        List<String> headers = new ArrayList<>(headerSet);

        // 2) Write CSV
        Writer w = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        writeRow(w, headers);

        List<String> cells = new ArrayList<>(headers.size());
        for (Map<String, Object> row : rows) {
            cells.clear();
            for (String key : headers) {
                Object v = (row == null) ? null : row.get(key);
                cells.add(toCell(v));
            }
            writeRow(w, cells);
        }
        w.flush();
    }

    private static String toCell(Object v) {
        if (v == null) return "";
        if (v instanceof String || v instanceof Number || v instanceof Boolean) return String.valueOf(v);
        if (v instanceof Map || v instanceof Collection || v.getClass().isArray()) {
            throw new IllegalArgumentException("Nested JSON not supported for flat export: " + v.getClass().getSimpleName());
        }
        return String.valueOf(v);
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

    public static void main(String[] args) {
        String json = "[{\"b\":2,\"a\":\"x\"},{\"a\":\"ds\\\"y\",\"c\":true}]";
        try {
            jsonArrayToCsv(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)), System.out);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
