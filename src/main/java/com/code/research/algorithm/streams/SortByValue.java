package com.code.research.algorithm.streams;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SortByValue {

    /**
     * Returns a LinkedHashMap containing the entries of the input map
     * sorted by value in descending order.
     *
     * @param map the input Map<String, Integer>
     * @return LinkedHashMap<String, Integer> sorted by descending values
     */
    public static LinkedHashMap<String, Integer> sortByValueDesc(Map<String, Integer> map) {
        return map.entrySet().stream()
                // Sort entries by value in reverse (descending) order
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                // Collect into a LinkedHashMap to preserve insertion (sorted) order
                .collect(Collectors.toMap(
                        Map.Entry::getKey,        // key mapper
                        Map.Entry::getValue,      // value mapper
                        (e1, e2) -> e1,           // merge function (won’t be used since no duplicates)
                        LinkedHashMap::new        // supplier of the result map
                ));
    }

    public static void main(String[] args) {
        Map<String, Integer> scores = Map.of(
                "alice", 42,
                "bob", 17,
                "carol", 58,
                "dave", 30
        );

        LinkedHashMap<String, Integer> sorted = sortByValueDesc(scores);
        sorted.forEach((name, score) ->
                System.out.printf("%s -> %d%n", name, score)
        );
        // Expected output:
        // carol -> 58
        // alice -> 42
        // dave  -> 30
        // bob   -> 17
    }
}
