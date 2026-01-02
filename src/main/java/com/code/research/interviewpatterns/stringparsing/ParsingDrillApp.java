package com.code.research.interviewpatterns.stringparsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * One-file string parsing drill (10 tasks) with a main() runner.
 * Copy-paste into a single file: ParsingDrill.java
 */
public final class ParsingDrillApp {

    // ---------------------------
    // Task 1: Hex #RRGGBB / #RGB -> RGB
    // ---------------------------
    static final class T1_HexToRgb {
        static int[] hexToRgb(String hex) {
            if (hex == null) throw new IllegalArgumentException("hex is null");
            hex = hex.trim();
            if (!hex.startsWith("#")) throw new IllegalArgumentException("must start with #");
            String s = hex.substring(1);
            if (s.length() == 3) {
                s = "" + s.charAt(0) + s.charAt(0)
                        + s.charAt(1) + s.charAt(1)
                        + s.charAt(2) + s.charAt(2);
            }
            if (s.length() != 6) throw new IllegalArgumentException("must be #RGB or #RRGGBB");
            int r = Integer.parseInt(s.substring(0, 2), 16);
            int g = Integer.parseInt(s.substring(2, 4), 16);
            int b = Integer.parseInt(s.substring(4, 6), 16);
            return new int[]{r, g, b};
        }
    }

    // ---------------------------
    // Task 2: Parse ISO date YYYY-MM-DD -> {y,m,d}
    // ---------------------------
    static final class T2_ParseIsoDate {
        static int[] parse(String s) {
            if (s == null) throw new IllegalArgumentException("date is null");
            s = s.trim();
            if (s.length() != 10 || s.charAt(4) != '-' || s.charAt(7) != '-')
                throw new IllegalArgumentException("bad date");
            int y = Integer.parseInt(s.substring(0, 4));
            int m = Integer.parseInt(s.substring(5, 7));
            int d = Integer.parseInt(s.substring(8, 10));
            if (m < 1 || m > 12 || d < 1 || d > 31) throw new IllegalArgumentException("range");
            return new int[]{y, m, d};
        }
    }

    // ---------------------------
    // Task 3: Parse time HH:MM:SS -> seconds
    // ---------------------------
    static final class T3_TimeToSeconds {
        static int toSeconds(String s) {
            if (s == null) throw new IllegalArgumentException("time is null");
            s = s.trim();
            if (s.length() != 8 || s.charAt(2) != ':' || s.charAt(5) != ':')
                throw new IllegalArgumentException("bad time");
            int hh = Integer.parseInt(s.substring(0, 2));
            int mm = Integer.parseInt(s.substring(3, 5));
            int ss = Integer.parseInt(s.substring(6, 8));
            if (hh < 0 || hh > 23 || mm < 0 || mm > 59 || ss < 0 || ss > 59)
                throw new IllegalArgumentException("range");
            return hh * 3600 + mm * 60 + ss;
        }
    }

    // ---------------------------
    // Task 4: Parse query string a=1&b=2 -> map
    // ---------------------------
    static final class T4_ParseQuery {
        static Map<String, String> parse(String q) {
            Map<String, String> map = new LinkedHashMap<>();
            if (q == null) return map;
            q = q.trim();
            if (q.isEmpty()) return map;

            String[] pairs = q.split("&");
            for (String p : pairs) {
                if (p.isEmpty()) continue;
                String[] kv = p.split("=", 2);
                String k = kv[0];
                String v = kv.length == 2 ? kv[1] : "";
                map.put(k, v.replace('+', ' ')); // simple decoding
            }
            return map;
        }
    }

    // ---------------------------
    // Task 5: Parse key=value;key2=value2 -> map
    // ---------------------------
    static final class T5_ParseSemicolonPairs {
        static Map<String, String> parse(String s) {
            Map<String, String> map = new LinkedHashMap<>();
            if (s == null) return map;
            s = s.trim();
            if (s.isEmpty()) return map;

            for (String part : s.split(";")) {
                part = part.trim();
                if (part.isEmpty()) continue;
                String[] kv = part.split("=", 2);
                String key = kv[0].trim();
                String val = kv.length == 2 ? kv[1].trim() : "";
                if (!key.isEmpty()) map.put(key, val);
            }
            return map;
        }
    }

    // ---------------------------
    // Task 6: Extract first integer from text
    // ---------------------------
    static final class T6_ExtractFirstInt {
        static int firstInt(String s) {
            if (s == null) throw new IllegalArgumentException("text is null");
            int n = s.length();
            int i = 0;
            while (i < n && !Character.isDigit(s.charAt(i))) i++;
            if (i == n) throw new IllegalArgumentException("no number");
            long val = 0;
            while (i < n && Character.isDigit(s.charAt(i))) {
                val = val * 10 + (s.charAt(i) - '0');
                if (val > Integer.MAX_VALUE) throw new IllegalArgumentException("overflow");
                i++;
            }
            return (int) val;
        }
    }

    // ---------------------------
    // Task 7: Normalize spaces "  a   b  " -> "a b"
    // ---------------------------
    static final class T7_NormalizeSpaces {
        static String normalize(String s) {
            if (s == null) return "";
            s = s.trim();
            if (s.isEmpty()) return "";
            StringBuilder out = new StringBuilder();
            boolean prevSpace = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == ' ') {
                    if (!prevSpace) out.append(' ');
                    prevSpace = true;
                } else {
                    out.append(c);
                    prevSpace = false;
                }
            }
            return out.toString();
        }
    }

    // ---------------------------
    // Task 8: Validate IPv4 (strict: no leading zeros)
    // ---------------------------
    static final class T8_ValidateIPv4 {
        static boolean isValid(String ip) {
            if (ip == null) return false;
            ip = ip.trim();
            String[] parts = ip.split("\\.", -1);
            if (parts.length != 4) return false;

            for (String p : parts) {
                if (p.isEmpty() || p.length() > 3) return false;
                for (int i = 0; i < p.length(); i++) if (!Character.isDigit(p.charAt(i))) return false;
                if (p.length() > 1 && p.charAt(0) == '0') return false; // strict
                int v = Integer.parseInt(p);
                if (v < 0 || v > 255) return false;
            }
            return true;
        }
    }

    // ---------------------------
    // Task 9: Simple CSV split (no quotes)
    // ---------------------------
    static final class T9_ParseCsvSimple {
        static List<String> parse(String line) {
            List<String> res = new ArrayList<>();
            if (line == null) return res;
            for (String part : line.split(",", -1)) res.add(part.trim());
            return res;
        }
    }

    // ---------------------------
    // Task 10: Parse log "[ERROR] 2026-01-02 msg" -> {level, msg}
    // ---------------------------
    static final class T10_ParseLog {
        static String[] parse(String s) {
            if (s == null) throw new IllegalArgumentException("log is null");
            s = s.trim();
            if (!s.startsWith("[")) throw new IllegalArgumentException("no [");
            int close = s.indexOf(']');
            if (close < 0) throw new IllegalArgumentException("no ]");

            String level = s.substring(1, close).trim();

            int firstSpace = s.indexOf(' ', close + 1);
            if (firstSpace < 0) return new String[]{level, ""};

            String rest = s.substring(firstSpace + 1).trim();

            // If rest begins with ISO date, drop it.
            if (rest.length() >= 10 && rest.charAt(4) == '-' && rest.charAt(7) == '-') {
                int nextSpace = rest.indexOf(' ', 10);
                rest = (nextSpace >= 0) ? rest.substring(nextSpace + 1).trim() : "";
            }

            return new String[]{level, rest};
        }
    }

    // ---------------------------
    // Runner
    // ---------------------------
    public static void main(String[] args) {
        System.out.println("=== String Parsing Drill (10 tasks) ===");

        // 1
        System.out.println("\n1) Hex -> RGB");
        System.out.println(Arrays.toString(T1_HexToRgb.hexToRgb("#ff00aa"))); // [255,0,170]
        System.out.println(Arrays.toString(T1_HexToRgb.hexToRgb("#0fa")));    // [0,255,170]

        // 2
        System.out.println("\n2) ISO date");
        System.out.println(Arrays.toString(T2_ParseIsoDate.parse("2026-01-02"))); // [2026,1,2]

        // 3
        System.out.println("\n3) Time to seconds");
        System.out.println(T3_TimeToSeconds.toSeconds("01:02:03")); // 3723

        // 4
        System.out.println("\n4) Query string to map");
        System.out.println(T4_ParseQuery.parse("a=1&b=2&c=hello+world"));

        // 5
        System.out.println("\n5) Semicolon pairs to map");
        System.out.println(T5_ParseSemicolonPairs.parse("id=10; name=Ann ; active=true;"));

        // 6
        System.out.println("\n6) Extract first int");
        System.out.println(T6_ExtractFirstInt.firstInt("orderId=12345, status=OK")); // 12345

        // 7
        System.out.println("\n7) Normalize spaces");
        System.out.println("'" + T7_NormalizeSpaces.normalize("  a   b   c  ") + "'"); // 'a b c'

        // 8
        System.out.println("\n8) Validate IPv4");
        System.out.println(T8_ValidateIPv4.isValid("192.168.0.1")); // true
        System.out.println(T8_ValidateIPv4.isValid("256.1.1.1"));   // false
        System.out.println(T8_ValidateIPv4.isValid("01.2.3.4"));    // false (strict)

        // 9
        System.out.println("\n9) Parse CSV simple");
        System.out.println(T9_ParseCsvSimple.parse("a, b, c,, d"));

        // 10
        System.out.println("\n10) Parse log line");
        System.out.println(Arrays.toString(T10_ParseLog.parse("[ERROR] 2026-01-02 Something failed")));
        System.out.println(Arrays.toString(T10_ParseLog.parse("[INFO] Hello")));
    }
}
