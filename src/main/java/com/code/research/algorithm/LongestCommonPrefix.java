package com.code.research.algorithm;

public final class LongestCommonPrefix {
    // Return the longest common prefix among all strings
    public static String longestCommonPrefix(String[] strs) {
        // start with the first word as the prefix
        String pref = strs[0];
        for (int i = 1; i < strs.length && !pref.isEmpty(); i++) {
            // shrink prefix until current word matches it
            while (!strs[i].startsWith(pref)) {
                pref = pref.substring(0, pref.length() - 1);
                // no common prefix
                if (pref.isEmpty()) {
                    break;
                }
            }
        }
        // longest common prefix (or "")
        return pref;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"})); // "fl"
        System.out.println(longestCommonPrefix(new String[]{"dog", "racecar", "car"}));    // ""
    }
}
