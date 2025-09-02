package com.code.research.livecoding.tasks.jsoncsv;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsonCsvCore {

    private static final char DELIM = ',';

    public static void jsonArrayToCsv(InputStream in, OutputStream out) throws IOException {
        // Read all (simple and fine for live-coding; streaming version is doable with the same parser)
        String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        if (!json.isEmpty() && json.charAt(0) == '\uFEFF') { // drop BOM if present
            json = json.substring(1);
        }

        // Parse
        MiniJsonParser p = new MiniJsonParser(json);
        List<Map<String, String>> rows = p.parseArrayOfFlatObjects();
        if (rows.isEmpty()) {
            return; // nothing to write
        }

        // Build sorted header (stable)
        SortedSet<String> headerSet = new TreeSet<>();
        for (Map<String, String> row : rows) headerSet.addAll(row.keySet());
        if (headerSet.isEmpty()) return;
        List<String> headers = new ArrayList<>(headerSet);

        // Write CSV
        Writer w = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        writeRow(w, headers);

        List<String> cells = new ArrayList<>(headers.size());
        for (Map<String, String> row : rows) {
            cells.clear();
            for (String h : headers) {
                String v = row.get(h);        // already stringified (numbers/booleans kept as source text)
                cells.add(v == null ? "" : v);
            }
            writeRow(w, cells);
        }
        w.flush();
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

    private JsonCsvCore() {
    }

    /**
     * Minimal JSON parser for the constrained task:
     * - Parses: array of objects; object keys are strings; values are string/number/boolean/null.
     * - Rejects nested arrays/objects as values.
     */
    private static final class MiniJsonParser {
        private final char[] s;
        private int i;

        MiniJsonParser(String src) {
            this.s = src.toCharArray();
            this.i = 0;
        }

        List<Map<String, String>> parseArrayOfFlatObjects() {
            skipWs();
            expect('[');
            skipWs();
            List<Map<String, String>> out = new ArrayList<>();
            if (peek() == ']') {
                i++; // empty array
                return out;
            }
            while (true) {
                out.add(parseObjectFlat());
                skipWs();
                char c = peek();
                if (c == ',') {
                    i++;
                    skipWs();
                    continue;
                }
                if (c == ']') {
                    i++;
                    break;
                }
                throw error("Expected ',' or ']' in array");
            }
            skipWs();
            if (!eof()) throw error("Trailing content after array");
            return out;
        }

        private Map<String, String> parseObjectFlat() {
            skipWs();
            expect('{');
            skipWs();
            Map<String, String> map = new LinkedHashMap<>();
            if (peek() == '}') {
                i++;
                return map;
            }
            while (true) {
                String key = parseString();
                skipWs();
                expect(':');
                skipWs();
                String val = parseScalarAsString(); // null => null; others as string
                map.put(key, val);
                skipWs();
                char c = peek();
                if (c == ',') {
                    i++;
                    skipWs();
                    continue;
                }
                if (c == '}') {
                    i++;
                    break;
                }
                throw error("Expected ',' or '}' in object");
            }
            return map;
        }

        private String parseScalarAsString() {
            char c = peek();
            if (c == '"') return parseString();
            if (c == '-' || isDigit(c)) return parseNumberToken();
            if (startsWith("true")) {
                i += 4;
                return "true";
            }
            if (startsWith("false")) {
                i += 5;
                return "false";
            }
            if (startsWith("null")) {
                i += 4;
                return null;
            }
            if (c == '{' || c == '[') throw error("Nested structures are not supported for flat export");
            throw error("Invalid scalar value");
        }

        private String parseString() {
            expect('"');
            StringBuilder b = new StringBuilder();
            while (!eof()) {
                char c = s[i++];
                if (c == '"') break;
                if (c == '\\') {
                    if (eof()) throw error("Unterminated escape");
                    char e = s[i++];
                    switch (e) {
                        case '"':
                            b.append('"');
                            break;
                        case '\\':
                            b.append('\\');
                            break;
                        case '/':
                            b.append('/');
                            break;
                        case 'b':
                            b.append('\b');
                            break;
                        case 'f':
                            b.append('\f');
                            break;
                        case 'n':
                            b.append('\n');
                            break;
                        case 'r':
                            b.append('\r');
                            break;
                        case 't':
                            b.append('\t');
                            break;
                        case 'u':
                            if (i + 4 > s.length) throw error("Bad unicode escape");
                            int cp = hex(s[i]) << 12 | hex(s[i + 1]) << 8 | hex(s[i + 2]) << 4 | hex(s[i + 3]);
                            i += 4;
                            b.append((char) cp);
                            break;
                        default:
                            throw error("Bad escape: \\" + e);
                    }
                } else {
                    b.append(c);
                }
            }
            return b.toString();
        }

        private String parseNumberToken() {
            int start = i;
            if (peek() == '-') i++;
            if (!isDigit(peek())) throw error("Digit expected");
            if (peek() == '0') {
                i++; // leading zero
            } else {
                while (isDigit(peek())) i++;
            }
            if (peek() == '.') {
                i++;
                if (!isDigit(peek())) throw error("Digit expected after decimal point");
                while (isDigit(peek())) i++;
            }
            char e = peek();
            if (e == 'e' || e == 'E') {
                i++;
                char sign = peek();
                if (sign == '+' || sign == '-') i++;
                if (!isDigit(peek())) throw error("Digit expected in exponent");
                while (isDigit(peek())) i++;
            }
            return new String(s, start, i - start);
        }

        private void expect(char ch) {
            if (peek() != ch) throw error("Expected '" + ch + "'");
            i++;
        }

        private char peek() {
            skipWs();
            if (eof()) return '\0';
            return s[i];
        }

        private void skipWs() {
            while (!eof()) {
                char c = s[i];
                if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
                    i++;
                    continue;
                }
                break;
            }
        }

        private boolean startsWith(String lit) {
            skipWs();
            if (i + lit.length() > s.length) return false;
            for (int k = 0; k < lit.length(); k++) if (s[i + k] != lit.charAt(k)) return false;
            return true;
        }

        private static boolean isDigit(char c) {
            return c >= '0' && c <= '9';
        }

        private static int hex(char c) {
            if (c >= '0' && c <= '9') return c - '0';
            if (c >= 'a' && c <= 'f') return 10 + (c - 'a');
            if (c >= 'A' && c <= 'F') return 10 + (c - 'A');
            throw new IllegalArgumentException("Invalid hex: " + c);
        }

        private boolean eof() {
            return i >= s.length;
        }

        private IllegalArgumentException error(String msg) {
            return new IllegalArgumentException(msg + " at pos " + i);
        }
    }

    public static void main(String[] args) {
        String json = """
            [
              {"b":2,"a":"x"},
              {"a":"y","c":true,"n":null},
              {"a":"with,comma","b":"with \\\"quote\\\"","z":"line\\nbreak"}
            ]
            """;
        try {
            jsonArrayToCsv(
                    new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)),
                    System.out
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
