package com.code.research.algorithm;

public final class FindFirstOccurrence {
    // Return the first index of 'needle' in 'haystack' (no indexOf); -1 if not found
    public static int strStr(String haystack, String needle) {
        // lengths
        int n = haystack.length();
        int m = needle.length();
        // empty needle by convention
        if (m == 0) {
            return 0;
        }
        // needle longer than haystack
        if (m > n) {
            return -1;
        }

        // naive scan: try each start i where a full needle could still fit
        for (int i = 0; i + m <= n; i++) {
            // match length so far
            int j = 0;
            // advance while equal
            while (j < m && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            // full match found at i
            if (j == m) {
                return i;
            }
        }
        // no match
        return -1;
    }

    // Return the first index of 'needle' in 'haystack', or -1 if not found
    public static int strStr2(String haystack, String needle) {
        // by convention, empty needle found at index 0
        if (needle.isEmpty()) {
            return 0;
        }
        // simplest & correct: use Java's built-in search
        return haystack.indexOf(needle);
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(strStr("sadbutsad", "sad")); // 0
        System.out.println(strStr("sadbuttrue", "ut")); // 4
        System.out.println(strStr("leetcode", "leeto")); // -1
    }
}
