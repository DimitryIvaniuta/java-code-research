package com.code.research.algorithm.test;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstring {

    private int longestUniqueSubsctringLength(String str) {
        // Map to store the last index at which each character appeared
        Map<Character, Integer> lastIndex = new HashMap<Character, Integer>();
        int maxLength = 0;
        // window start pointer
        int start = 0;
        for(int end = 0; end < str.length(); end++) {
            char c = str.charAt(end);
            if(lastIndex.containsKey(c) && lastIndex.get(c) > start) {
                start = lastIndex.get(c) + 1;
            }
            lastIndex.put(c, end);
            maxLength = Math.max(maxLength, end - start);
        }
        return maxLength;
    }
}
