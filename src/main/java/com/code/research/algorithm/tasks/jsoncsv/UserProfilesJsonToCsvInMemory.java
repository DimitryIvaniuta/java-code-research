package com.code.research.algorithm.tasks.jsoncsv;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class UserProfilesJsonToCsvInMemory {

    private static final char DELIM = ',';
    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * Convert a JSON array of user profiles to CSV.
     * - Union of keys across all rows, lexicographically sorted
     * - Nested objects flattened as dot paths (e.g., address.city)
     * - Arrays of scalars joined with ';' (arrays of objects -> error)
     * Streams are NOT closed by this method.
     */
    public static void jsonProfilesToCsv(InputStream in, OutputStream out) throws IOException {
        // 1) Load whole input (memory is fine per requirement)
        String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        if (!json.isEmpty() && json.charAt(0) == '\uFEFF') {
            json = json.substring(1); // strip BOM if present
        }

        JsonNode root = MAPPER.readTree(json);
        if (root == null || !root.isArray()) {
            throw new IllegalArgumentException("Expected a JSON array of objects");
        }

        // 2) Flatten rows + collect union header
        List<Map<String, String>> rows = new ArrayList<>();
        SortedSet<String> headerSet = new TreeSet<>();
        for (JsonNode node : root) {
            if (!node.isObject()) {
                throw new IllegalArgumentException("Expected a JSON object");
            }
            Map<String, String> flat = new LinkedHashMap<>();
            flatten(node, "", flat);
            List<String> cells = new ArrayList<>();
        }
    }

    private static void flatten(JsonNode node, String prefix, Map<String, String> out) {
        if (node == null || node.isNull()) {
            if (!prefix.isEmpty()) {
                out.put(prefix, "");
            }
            return;
        }
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                String path = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
                flatten(entry.getValue(), path, out);
            });
            return;
        }
        if(node.isArray()) {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for(JsonNode child : node) {
                if (!first) {
                    sb.append(";");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String json = """
                [
                  {"name":"Alice","email":"alice@example.com","address":{"city":"Warsaw","zip":"00-001"},"tags":["pro","beta"],"age":31},
                  {"name":"Bob","address":{"city":"Gdańsk"},"phone":"+48-111-222-333","tags":[]},
                  {"name":"Carol","email":"carol@example.com","address":{"city":"Kraków","street":"Floriańska 1"},"newsletter":true}
                ]
                """;
        jsonProfilesToCsv(
                new java.io.ByteArrayInputStream(json.getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                System.out
        );
    }
}
