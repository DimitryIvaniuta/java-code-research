package com.code.research.datastructures.algorithm.longeststring;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LongestUniqueSubstring {

    /**
     * Returns the length of the longest substring
     * of s without repeating characters.
     *
     * @param s input string
     * @return max length of a substring with all distinct characters
     */
    public static int lengthOfLongestSubstring(String s) {
        log.info("str: {}", s);
        // Map to store the last index at which each character appeared
        Map<Character, Integer> lastIndex = new HashMap<>();
        int maxLen = 0;
        // window start pointer
        int start = 0;

        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            // If we've seen this character, and it's inside the current window,
            // move the start just past its last occurrence.
            if (lastIndex.containsKey(c) && lastIndex.get(c) >= start) {
                start = lastIndex.get(c) + 1;
                log.info("char: {} start: {} end: {}", c, start, end);
            }
            // Update (or add) the last seen index of c
            lastIndex.put(c, end);
            // Window length is end–start+1
            maxLen = Math.max(maxLen, end - start + 1);
        }

        return maxLen;
    }

}
