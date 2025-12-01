package com.code.research.algorithm;

public final class AnagramCheck {
    // Check if two strings are anagrams WITHOUT sorting (ASCII)
    public static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;   // lengths must match
        int[] cnt = new int[256];                     // frequency diff per char
        for (int i = 0; i < a.length(); i++) {
            cnt[a.charAt(i)]++;                       // count from a
            cnt[b.charAt(i)]--;                       // subtract from b
        }
        for (int x : cnt) if (x != 0) return false;   // any mismatch → not an anagram
        return true;                                  // all zero → anagram
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(isAnagram("listen", "silent")); // true
        System.out.println(isAnagram("rat", "car"));       // false
    }
}
