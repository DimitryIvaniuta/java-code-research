package com.code.research.algorithm;

public class AnagramDeletionsApp {

    public static void main(String[] args) {
        // Predefined test strings
        String a1 = "cde";
        String b1 = "abc";

        String a2 = "abc";
        String b2 = "abc";

        System.out.println("Test 1: a = " + a1 + ", b = " + b1);
        System.out.println("Min deletions = " + minDeletionsToMakeAnagram(a1, b1));

        System.out.println("Test 2: a = " + a2 + ", b = " + b2);
        System.out.println("Min deletions = " + minDeletionsToMakeAnagram(a2, b2));
    }

    /**
     * Returns the minimum number of character deletions needed
     * to make two strings anagrams of each other.
     * Assumes lowercase letters a–z.
     */
    private static int minDeletionsToMakeAnagram(String a, String b) {
        int[] freq = new int[26];

        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            if (c >= 'a' && c <= 'z') {
                freq[c - 'a']++;
            }
        }

        for (int i = 0; i < b.length(); i++) {
            char c = b.charAt(i);
            if (c >= 'a' && c <= 'z') {
                freq[c - 'a']--;
            }
        }

        int deletions = 0;
        for (int i = 0; i < 26; i++) {
            deletions += Math.abs(freq[i]);
        }
        return deletions;
    }
}
