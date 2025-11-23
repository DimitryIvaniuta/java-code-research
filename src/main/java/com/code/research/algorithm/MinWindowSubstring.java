package com.code.research.algorithm;

public final class MinWindowSubstring {
    // Return the smallest substring of s that contains all chars of t (with multiplicity)
    public static String minWindow(String s, String t) {
        // impossible if t longer than s
        if (t.length() > s.length()) {
            return "";
        }

        // ASCII freq needed for t
        int[] need = new int[128];
        // count each required char
        for (char c : t.toCharArray()) {
            need[c]++;
        }

        // how many chars still missing
        int miss = t.length();
        // best window [bestL, bestL+bestLen)
        int bestL = 0;
        int bestLen = Integer.MAX_VALUE;

        // sliding window [l..r)
        for (int l = 0, r = 0; r < s.length(); r++) {
            // expand right
            char cr = s.charAt(r);
            // this char satisfied one need
            if (need[cr]-- > 0) {
                miss--;
            }

            // when all required chars are in window, try to shrink from left
            while (miss == 0) {
                // update best if shorter
                if (r - l + 1 < bestLen) {
                    bestL = l;
                    bestLen = r - l + 1;
                }
                // pop left char
                char cl = s.charAt(l++);
                // window lost a needed char
                if (++need[cl] > 0) {
                    miss++;
                }
            }
        }
        return (bestLen == Integer.MAX_VALUE) ? "" : s.substring(bestL, bestL + bestLen);
    }

    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC", "ABC")); // BANC
        System.out.println(minWindow("a", "a"));               // a
        System.out.println(minWindow("a", "aa"));              // ""
    }
}
