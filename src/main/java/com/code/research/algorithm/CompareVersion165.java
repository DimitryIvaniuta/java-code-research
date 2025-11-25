package com.code.research.algorithm;

public final class CompareVersion165 {
    // Compare two version strings: return -1 if v1<v2, 1 if v1>v2, else 0
    public static int compareVersion(String v1, String v2) {
        // split revisions by '.'
        String[] a = v1.split("\\.");
        // split revisions by '.'
        String[] b = v2.split("\\.");
        // compare up to the longer length
        int n = Math.max(a.length, b.length);

        for (int i = 0; i < n; i++) {
            // missing revisions treated as 0
            String r1 = (i < a.length) ? strip(a[i]) : "0";
            String r2 = (i < b.length) ? strip(b[i]) : "0";

            // First compare by length (avoids integer overflow)
            if (r1.length() != r2.length())
                return r1.length() < r2.length() ? -1 : 1;

            // Same length → lexicographic compare works as numeric compare
            int cmp = r1.compareTo(r2);
            // different → decide
            if (cmp != 0) {
                return cmp < 0 ? -1 : 1;
            }
        }
        // all revisions equal
        return 0;
    }

    // Remove leading zeros; if all zeros → return "0"
    private static String strip(String s) {
        int i = 0, n = s.length();
        // skip leading zeros
        while (i < n && s.charAt(i) == '0') {
            i++;
        }
        // keep at least a single '0'
        return (i == n) ? "0" : s.substring(i);
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(compareVersion("1.2", "1.10"));      // -1
        System.out.println(compareVersion("1.01", "1.001"));    // 0
        System.out.println(compareVersion("1.0", "1.0.0.0"));   // 0
        System.out.println(compareVersion("2.0.1", "2.0.0.9")); // 1
    }
}
