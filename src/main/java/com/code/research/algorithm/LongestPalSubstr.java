package com.code.research.algorithm;

public final class LongestPalSubstr {                       // Final utility class for longest palindromic substring
    public static String longestPalindrome(String s) {      // Entry method: returns the longest palindromic substring of s
        int n = s.length(), L = 0, R = 0;                   // n = length; [L,R] stores best palindrome bounds (inclusive)

        for (int i = 0; i < n; i++) {                       // Try every index as a center
            int l = i, r = i;                               // Odd-length center at i (single character center)
            while (l >= 0 && r < n &&                       // Expand while in bounds
                    s.charAt(l) == s.charAt(r)) {            // and characters match
                if (r - l > R - L) {
                    L = l;
                    R = r;
                }        // Update best bounds if this palindrome is longer
                l--;
                r++;                                   // Expand outward
            }

            l = i;
            r = i + 1;                               // Even-length center between i and i+1
            while (l >= 0 && r < n &&                       // Expand while in bounds
                    s.charAt(l) == s.charAt(r)) {            // and characters match
                if (r - l > R - L) {
                    L = l;
                    R = r;
                }        // Update best bounds if longer
                l--;
                r++;                                   // Expand outward
            }
        }

        return s.substring(L, R + 1);                       // Extract longest palindrome using inclusive [L,R]
    }                                                       // End of longestPalindrome
}                                                           // End of class
