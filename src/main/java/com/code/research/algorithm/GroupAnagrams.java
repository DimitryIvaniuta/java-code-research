package com.code.research.algorithm;

import java.util.*;

public final class GroupAnagrams {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> byKey = new HashMap<>();
        for (String s : strs) {
            char[] a = s.toCharArray();
            Arrays.sort(a);                    // O(L log L)
            String key = new String(a);
            byKey.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(byKey.values());
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println(groupAnagrams(new String[]{""}));
        System.out.println(groupAnagrams(new String[]{"a"}));
    }
}
