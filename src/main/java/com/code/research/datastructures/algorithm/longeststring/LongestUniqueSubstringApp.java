package com.code.research.datastructures.algorithm.longeststring;

import lombok.extern.slf4j.Slf4j;

import static com.code.research.datastructures.algorithm.longeststring.LongestUniqueSubstring.lengthOfLongestSubstring;

@Slf4j
public class LongestUniqueSubstringApp {
    public static void main(String[] args) {
        String[] tests = {
                "abcabcbb",
                "bbbbb",
                "pwwkew",
                "",          // empty string
                " ",         // single space
                "au",        // two distinct chars
                "dvdf"       // tricky: "vdf" = 3
        };

        for (String t : tests) {
            int result = lengthOfLongestSubstring(t);
            log.info("Input: \"{}\" → Output: {}", t, result);
        }
    }
}
