package com.code.research.datastructures.algorithm.dynamic;

/**
 * LongestCommonSubsequence provides a dynamic programming solution for finding
 * the longest common subsequence (LCS) between two strings.
 *
 * <p>The algorithm builds a DP table where each cell dp[i][j] represents the length
 * of the LCS of the substrings s1[0..i-1] and s2[0..j-1]. The LCS is then reconstructed
 * by backtracking through the DP table.
 *
 * <p>Real-world applications include file diff tools, DNA sequence analysis, and version control.
 */
public class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        //
    }

    /**
     * Computes the longest common subsequence (LCS) between two strings.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return the LCS as a string; returns an empty string if there is no common subsequence
     */
    public static String computeLCS(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        // dp[i][j] holds the length of LCS of s1[0..i-1] and s2[0..j-1].
        int[][] dp = new int[m + 1][n + 1];

        // Build the DP table.
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // If the characters match, add 1 to the LCS length of the previous substrings.
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // Otherwise, take the maximum LCS length from either excluding the current char from s1 or s2.
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Reconstruct the LCS from the DP table by backtracking.
        StringBuilder lcs = new StringBuilder();
        int i = m;
        int j = n;
        while (i > 0 && j > 0) {
            // If the characters are equal, they are part of the LCS.
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcs.append(s1.charAt(i - 1));
                i--;
                j--;
            } else
                // Move in the direction of the larger subproblem.
                if (dp[i - 1][j] >= dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }

        }
        // The LCS is built backwards, so reverse it.
        return lcs.reverse().toString();
    }

}
