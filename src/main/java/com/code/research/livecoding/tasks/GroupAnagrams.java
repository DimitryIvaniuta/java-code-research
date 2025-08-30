package com.code.research.livecoding.tasks;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class GroupAnagrams {

    /**
     * Groups anagrams together. Case-insensitive, stable group order (first appearance).
     * Example: ["eat","Tea","ate","tan","nat","bat"] -> [[eat, Tea, ate], [tan, nat], [bat]]
     */
    public static List<List<String>> groupAnagrams(List<String> words) {
        if (words == null || words.isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, List<String>> groups = words.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.groupingBy(
                                GroupAnagrams::keyOf,
                                LinkedHashMap::new,
                                Collectors.toList()
                        )
                );

        return new ArrayList<>(groups.values());
    }

    // Build an anagram key by lowercasing and sorting Unicode code points.
    private static String keyOf(String s) {
        return s.toLowerCase(Locale.ROOT)
                .codePoints()
                .sorted()
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                ).toString();
    }

    public static void main(String[] args) {
        List<String> in = Arrays.asList("eat", "Tea", "ate", "ba😀",
                "tan", "nat", "bat", "a😀b", "b😀a");
        // [[eat, Tea, ate], [ba😀, a😀b, b😀a], [tan, nat], [bat]]
        log.info("Group Anagrams: {}", groupAnagrams(in));
    }
}
