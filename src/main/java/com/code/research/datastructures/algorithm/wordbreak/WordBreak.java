package com.code.research.datastructures.algorithm.wordbreak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreak {

    /**
     * Fast O(n^2) DP solution to decide if s can be segmented.
     */
    public static boolean wordBreak(String s, Set<String> dict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;  // empty prefix

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    /**
     * DFS + memo to collect all possible segmentations of s.
     * Returns a list of sentences, each sentence is a valid segmentation.
     */
    public static List<String> wordBreakAll(String s, Set<String> dict) {
        return dfs(s, dict, new HashMap<>());
    }

    private static List<String> dfs(String s, Set<String> dict, Map<String, List<String>> memo) {
        if (memo.containsKey(s)) {
            return memo.get(s);
        }

        List<String> res = new ArrayList<>();
        if (s.isEmpty()) {
            res.add("");  // one valid segmentation of the empty string
            return res;
        }

        for (int end = 1; end <= s.length(); end++) {
            String prefix = s.substring(0, end);
            if (dict.contains(prefix)) {
                String remainder = s.substring(end);
                List<String> suffixWays = dfs(remainder, dict, memo);
                for (String way : suffixWays) {
                    String sentence = prefix + (way.isEmpty() ? "" : " " + way);
                    res.add(sentence);
                }
            }
        }

        memo.put(s, res);
        return res;
    }

}
