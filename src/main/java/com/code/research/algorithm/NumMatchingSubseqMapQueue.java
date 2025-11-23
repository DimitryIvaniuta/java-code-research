package com.code.research.algorithm;

import java.util.*;

/**
 * Number of Matching Subsequences
 * Map<Character, Queue<State>> version (“bucketed waiting lists”).
 * Time:  O(|s| + total length of words)
 * Space: O(total length of words)
 */
public final class NumMatchingSubseqMapQueue {
    public static int numMatchingSubseq(String s, String[] words) {
        // Preconvert words to char arrays for speed
        char[][] W = new char[words.length][];
        for (int i = 0; i < words.length; i++) W[i] = words[i].toCharArray();

        // Buckets: for each character, a queue of (wordIndex, nextPosToMatch)
        Map<Character, ArrayDeque<int[]>> buckets = new HashMap<>(26);
        for (char c = 'a'; c <= 'z'; c++) buckets.put(c, new ArrayDeque<>());

        // Initialize: put each word into the bucket of its first required character
        for (int i = 0; i < W.length; i++) {
            buckets.get(W[i][0]).offer(new int[]{ i, 0 });
        }

        int matched = 0;

        // Scan s left-to-right; for each char, advance all waiting words
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            ArrayDeque<int[]> q = buckets.get(c);
            int size = q.size();                // only process items that were waiting BEFORE this char
            for (int t = 0; t < size; t++) {
                int[] state = q.poll();         // (wordIndex, pos)
                int wIdx = state[0];
                int pos  = state[1] + 1;        // matched this char, move to next position

                if (pos == W[wIdx].length) {
                    matched++;                  // word finished -> count it
                } else {
                    char nextNeed = W[wIdx][pos];
                    buckets.get(nextNeed).offer(new int[]{ wIdx, pos });
                }
            }
        }
        return matched;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(numMatchingSubseq("abcde", new String[]{"a","bb","acd","ace"})); // 3
        System.out.println(numMatchingSubseq("dsahjpjauf", new String[]{"ahjpjau","ja","ahbwzgqnuk","tnmlanowax"})); // 2
    }
}
