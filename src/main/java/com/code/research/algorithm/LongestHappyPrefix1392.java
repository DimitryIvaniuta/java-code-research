package com.code.research.algorithm;

public final class LongestHappyPrefix1392 {
    // Return the longest non-empty prefix of s that is also a suffix (not the whole string)
    public static String longestPrefix(String s) {
        // length of the string
        int n = s.length();
        // no non-empty proper prefix for length 0/1
        if (n <= 1) {
            return "";
        }
        // KMP prefix-function (longest proper prefix = suffix up to i)
        int[] lps = new int[n];
        // current length of matched prefix
        int j = 0;

        // build lps from left to right
        for (int i = 1; i < n; i++) {
            // fallback on mismatch
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = lps[j - 1];
            }
            // extend match
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            // store best border length at i
            lps[i] = j;
        }
        // longest border of whole string
        return s.substring(0, lps[n - 1]);
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(longestPrefix("level"));   // "l"
        System.out.println(longestPrefix("ababab"));  // "abab"
        System.out.println(longestPrefix("abc"));     // ""
    }
}
