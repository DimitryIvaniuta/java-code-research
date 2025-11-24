package com.code.research.algorithm;

import java.util.ArrayDeque;

public final class FirstNonRepeatingStream {
    // Return a string where i-th char is the first non-repeating char in s[0..i] (or '#')
    public static String firstNonRepeating(String s) {
        // frequency of each lowercase letter
        int[] freq = new int[26];
        // queue of candidates (in arrival order)
        ArrayDeque<Character> q = new ArrayDeque<>();
        StringBuilder ans = new StringBuilder(s.length());

        for (int i = 0; i < s.length(); i++) {
            // current character
            char c = s.charAt(i);
            // update frequency
            freq[c - 'a']++;
            // push as a candidate
            q.offer(c);

            // drop all repeated chars from the front
            while (!q.isEmpty() && freq[q.peek() - 'a'] > 1) q.poll();

            // first non-repeating (queue front) or '#'
            ans.append(q.isEmpty() ? '#' : q.peek());
        }
        return ans.toString();
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(firstNonRepeating("aabc")); // a#bb
        System.out.println(firstNonRepeating("bb"));   // b#
    }
}
