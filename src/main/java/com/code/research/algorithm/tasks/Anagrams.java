package com.code.research.algorithm.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Anagrams {

    public static List<List<String>> groupAnagrams(List<String> in) {
        if (in == null || in.isEmpty()) {
            return List.of();
        }
        Map<String, List<String>> byKey = in.stream()
                .filter(w -> !w.isEmpty())
                .collect(Collectors.groupingBy(
                        Anagrams::normalizeKey,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
        return new ArrayList<>(byKey.values());
    }

    private static String normalizeKey(String in) {
        char[] chars = in.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) {
        List<String> in = List.of("eat", "tea", "tan", "ate", "nat", "bat");
        System.out.println(groupAnagrams(in));
        // [[eat, tea, ate], [tan, nat], [bat]]
    }
}
