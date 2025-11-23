package com.code.research.algorithm;

public final class ValidAnagram {
    // Return true if t is an anagram of s (lowercase a–z)
    public static boolean isAnagram(String s, String t) {
        // different sizes → cannot be anagrams
        if (s.length() != t.length()) {
            return false;
        }
        // frequency diff for letters 'a'..'z'
        int[] cnt = new int[26];
        // single pass over both strings
        for (int i = 0; i < s.length(); i++) {
            // count char from s
            cnt[s.charAt(i) - 'a']++;
            // subtract char from t
            cnt[t.charAt(i) - 'a']--;
        }
        // any nonzero means mismatch
        for (int x : cnt) {
            if (x != 0) {
                return false;
            }
        }
        // all zeros → anagram
        return true;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "nagaram")); // true
        System.out.println(isAnagram("rat", "car"));         // false
    }
}
