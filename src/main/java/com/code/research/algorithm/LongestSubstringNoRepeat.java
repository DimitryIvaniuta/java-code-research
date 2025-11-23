package com.code.research.algorithm;

import java.util.HashMap;
import java.util.Map;

public final class LongestSubstringNoRepeat {
    // Generic (Unicode-safe): uses a map of last seen positions
    public static int lengthOfLongestSubstring(String s) {
        System.out.println("S: " + s);
        Map<Character, Integer> last = new HashMap<>();
        int best = 0, start = 0; // window [start..i]
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer j = last.put(c, i);
            System.out.println("j: " + j);
            if (j != null && j >= start) start = j + 1;   // move start past duplicate
            best = Math.max(best, i - start + 1);
        }
        return best;
    }

    // Fast ASCII variant (if you know input is ASCII)
    public static int lengthOfLongestSubstringAscii(String s) {
        int[] last = new int[128];
        java.util.Arrays.fill(last, -1);
        int best = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int j = last[c];
            if (j >= start) start = j + 1;
            last[c] = i;
            best = Math.max(best, i - start + 1);
        }
        return best;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(lengthOfLongestSubstring("bbbbb"));    // 1
        System.out.println(lengthOfLongestSubstring("pwwkew"));   // 3
    }
}


/*
        Map<Character, Integer> last = new HashMap<>();
        int best = 0;
        int start = 0;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            Integer j = last.put(c, i);
            if(j != null && j >= start){
                start = j + 1;
            }
            best = Math.max(best, i - start + 1);
        }
        return best;
 */