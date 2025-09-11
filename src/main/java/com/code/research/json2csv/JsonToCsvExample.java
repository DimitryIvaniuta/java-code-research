package com.code.research.json2csv;

import com.fasterxml.jackson.databind.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsonToCsvExample {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final char DELIM = ',';

    public static void main(String[] args) throws Exception {
        String json = """
                [{"id":2,"name":"Alice","email":"alice@example.com"},
                  {"name":"Bob","id":1,"phone":"+48-111-222-333"},
                  {"id":3,"name":"Carol","email":"carol@example.com","age":30}]
                """;
        // 1) Parse array
        JsonNode root = MAPPER.readTree(json);
        if (!root.isArray()) throw new IllegalArgumentException("Expected JSON array");

        // 2) Collect union of keys
        SortedSet<String> headerSet = new TreeSet<>();
        List<Map<String, String>> rows = new ArrayList<>();
        for (JsonNode node : root) {
            if (!node.isObject()) continue;
            Map<String, String> flat = new LinkedHashMap<>();
            node.fieldNames().forEachRemaining(fn ->
                    flat.put(fn, node.get(fn).isNull() ? "" : node.get(fn).asText())
            );
            headerSet.addAll(flat.keySet());
            rows.add(flat);
        }
        // 3) Write CSV to stdout
        List<String> headers = new ArrayList<>(headerSet);
        try (Writer w = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8))) {
            writeRow(w, headers);
            for (Map<String, String> row : rows) {
                List<String> cells = new ArrayList<>();
                for (String h : headers) cells.add(row.getOrDefault(h, ""));
                writeRow(w, cells);
            }
        }
    }

    private static void writeRow(Writer w, List<String> cols) throws IOException {
        for (int i = 0; i < cols.size(); i++) {
            if (i > 0) w.write(DELIM);
            w.write(escape(cols.get(i)));
        }
        w.write('\n');
    }

    private static String escape(String s) {
        if (s.indexOf(DELIM) >= 0 || s.indexOf('"') >= 0 || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }
}

