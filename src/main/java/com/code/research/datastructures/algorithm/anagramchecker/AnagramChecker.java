package com.code.research.datastructures.algorithm.anagramchecker;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnagramChecker {

    public static boolean isAnagram(String s1, String s2) {
        if (s1 == null || s2 == null
                || s1.length() != s2.length()
        ) {
            return false;
        }

        // Count occurrences of each character in s1 and s2.
        // Here we assume ASCII, so array size = 256.
        int[] counts = new int[256];
        for (int i = 0; i < s1.length(); i++) {
            counts[s1.charAt(i)]++;
            counts[s1.charAt(i)]--;
        }
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnagramUnicodeSafe(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        // Remove spaces/punctuation and normalize case (optional)
        String f1 = s1.replaceAll("\\W+", "").toLowerCase();
        String f2 = s2.replaceAll("\\W+", "").toLowerCase();
        if (f1.length() != f2.length()) {
            return false;
        }
        Map<Character, Integer> mapCharCount = new HashMap<>();
        for (int i = 0; i < f1.length(); i++) {
            char c1 = f1.charAt(i);
            char c2 = f2.charAt(i);
            mapCharCount.merge(c1, 1, Integer::sum);
            mapCharCount.merge(c2, -1, Integer::sum);
        }
        return mapCharCount.values().stream().allMatch(count -> count == 0);
    }

    /**
     * Uses Java 8 streams to count codepoint frequencies and compare maps.
     */
    public static boolean isAnagramByStream(String s1, String s2) {
        if (s1 == null || s2 == null
                || s1.length() != s2.length()
        ) {
            return false;
        }
        String f1 = s1.toLowerCase().replaceAll("[^a-zA-Z]","");
        String f2 = s2.toLowerCase().replaceAll("[^a-zA-Z]","");
        Map<Integer, Long> freq1 = f1.codePoints()
                .boxed()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );
        Map<Integer, Long> freq2 = f2.codePoints()
                .boxed()
                .collect(
                        Collectors.groupingBy(Function.identity(),
                                Collectors.counting()
                        ));

        return freq1.equals(freq2);
    }

    public static boolean isAnagramByMap(String s1, String s2) {
        if (s1 == null || s2 == null
                || s1.length() != s2.length()
        ) {
            return false;
        }
        String f1 = s1.toLowerCase().replaceAll("[^a-zA-Z]","");
        String f2 = s2.toLowerCase().replaceAll("[^a-zA-Z]","");

        Map<Integer, Long> freq1 = new HashMap<>();
        f1.codePoints()
                .forEach(c -> freq1.put(c, freq1.getOrDefault(c, 0L) + 1));
        Map<Integer, Long> freq2 = new HashMap<>();
        f2.codePoints()
                .forEach(c -> freq2.put(c, freq2.getOrDefault(c, 0L) + 1));

        return freq1.equals(freq2);
    }
}
