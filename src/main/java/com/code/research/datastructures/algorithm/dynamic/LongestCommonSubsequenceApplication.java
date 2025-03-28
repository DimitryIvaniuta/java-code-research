package com.code.research.datastructures.algorithm.dynamic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongestCommonSubsequenceApplication {

    /**
     * Main method to demonstrate the usage of the LCS algorithm.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String s1 = "ABCBDAB";
        String s2 = "BDCABA";

        String lcsResult = LongestCommonSubsequence.computeLCS(s1, s2);
        log.info("Longest Common Subsequence of \"{}\" and \"{}\" is: {}", s1, s2, lcsResult);
    }

}
