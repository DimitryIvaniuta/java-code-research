package com.code.research.algorithm;

public final class ReverseWords151 {
    // Return the words of s in reverse order, single-spaced
    public static String reverseWords(String s) {
        // trim edges; split on 1+ spaces → words only
        String[] w = s.trim().split("\\s+");
        // builder for result
        StringBuilder out = new StringBuilder();
        // append words from last to first
        for (int i = w.length - 1; i >= 0; i--) {
            // add word
            out.append(w[i]);
            // single space between words (not after last)
            if (i > 0) {
                out.append(' ');
            }
        }
        // final reversed string
        return out.toString();
    }

    // Return the words of s in reverse order, single-spaced, using a String[] buffer
    public static String reverseWords2(String s) {
        // trim edges; split on 1+ spaces → clean words
        String[] words = s.trim().split("\\s+");
        // output array (reversed order)
        String[] out = new String[words.length];
        // fill out[] with reversed words
        for (int i = 0; i < words.length; i++) {
            out[i] = words[words.length - 1 - i];
        }
        // join with single spaces
        return String.join(" ", out);
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(reverseWords("the sky is blue"));      // "blue is sky the"
        System.out.println(reverseWords("  hello world  "));      // "world hello"
        System.out.println(reverseWords("a good   example"));     // "example good a"
    }
}
