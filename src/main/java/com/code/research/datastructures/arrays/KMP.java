package com.code.research.datastructures.arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KMP {

    // Preprocess the pattern to generate the LPS (Longest Prefix Suffix) array.
    private static int[] computeLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        int i = 1;
        lps[0] = 0;  // lps[0] is always 0

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    // Search for the pattern in the text using the KMP algorithm.
    public static int kmpSearch(String text, String pattern) {
        int[] lps = computeLPS(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == pattern.length()) {
                return i - j;  // Pattern found at index (i - j)
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return -1;  // Pattern not found
    }

    public static void main(String[] args) {
        String text = "ABA BDABACD  ABABACABABCABAB ABAB";
        String pattern = "ABABCABAB";
        int index = kmpSearch(text, pattern);

        if (index != -1) {
            log.info("Pattern found at index: {}", index);
        } else {
            log.info("Pattern not found in the text.");
        }
    }
}
