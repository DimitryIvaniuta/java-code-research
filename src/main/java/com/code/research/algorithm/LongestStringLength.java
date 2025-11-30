package com.code.research.algorithm;

import java.util.HashMap;
import java.util.Map;

public class LongestStringLength {

    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLength = 0;
        int windowStart = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // If we've seen this char before, and it's inside the current window,
            // move the window start to one position after its last seen index.
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= windowStart) {
                windowStart = lastSeen.get(c) + 1;
            }
            lastSeen.put(c, i);
            maxLength = Math.max(maxLength, i - windowStart + 1);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        String[] tests = {
                "abcabcbb",
                "bbbbb",
                "pwwkew",
                "",
                "au",
                "dvdf"
        };

        for (String test : tests) {
            System.out.printf("Input: \"%s\" → Output: %d%n",
                    test, lengthOfLongestSubstring(test));
        }
        // Expected outputs:
        // "abcabcbb" → 3 ("abc")
        // "bbbbb"    → 1 ("b")
        // "pwwkew"   → 3 ("wke")
        // ""         → 0
        // "au"       → 2 ("au")
        // "dvdf"     → 3 ("vdf")
    }
}
