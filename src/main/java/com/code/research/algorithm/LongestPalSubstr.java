package com.code.research.algorithm;

public final class LongestPalSubstr {
    // Final utility class for longest palindromic substring
    public static String longestPalindrome(String s) {
        // Entry method: returns the longest palindromic substring of s
        // n = length; [l, r] store best palindrome bounds (inclusive)
        int n = s.length();
        int lBound = 0;
        int rBound = 0;
        // Try every index as a center
        for (int i = 0; i < n; i++) {
            // Odd-length center at i (single character center)
            int l = i;
            int r = i;
            // Expand while in bounds
            // and characters match
            while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                if (r - l > rBound - lBound) {
                    // Update best bounds if this palindrome is longer
                    lBound = l;
                    rBound = r;
                }
                l--;
                r++;                                   // Expand outward
            }

            l = i;
            // Even-length center between i and i+1
            r = i + 1;
            // and characters match
            while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                if (r - l > rBound - lBound) {
                    // Update best bounds if longer
                    lBound = l;
                    rBound = r;
                }
                // Expand outward
                l--;
                r++;
            }
        }
        // Extract longest palindrome using inclusive [L,R]
        return s.substring(lBound, rBound + 1);
    }
}
