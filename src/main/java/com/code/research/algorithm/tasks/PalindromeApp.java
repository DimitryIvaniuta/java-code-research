package com.code.research.algorithm.tasks;

public class PalindromeApp {

    /**
     * Computes the length of the longest palindromic subsequence in the given string.
     * Uses dynamic programming with O(n^2) time and space complexity.
     *
     * @param s the input string
     * @return the length of the longest palindromic subsequence, or 0 if s is null or empty
     */
    public static int longestPalindromicSubsequence(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int n = s.length();
        // dp[i][j] will hold the length of LPS in s[i..j]
        int[][] dp = new int[n][n];

        // Subsequences of length 1 are palindromes of length 1
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        // Build up from substrings of length 2 to n
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                char left = s.charAt(i);
                char right = s.charAt(j);

                if (left == right) {
                    if (len == 2) {
                        // Two-character palindrome
                        dp[i][j] = 2;
                    } else {
                        // Extend the inner palindrome by 2
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    }
                } else {
                    // Not matching: take the max of dropping one end
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        // The result for the whole string is in dp[0][n-1]
        return dp[0][n - 1];
    }

    // Simple demonstration
    public static void main(String[] args) {
        String[] tests = {
                null,
                "",
                "BBABCBCAB",
                "agbdba",
                "character",
                "cbbd"
        };

        for (String test : tests) {
            int lpsLength = longestPalindromicSubsequence(test);
            System.out.printf("Input: %-12s → LPS length: %d%n",
                    test, lpsLength);
        }
    }
}
