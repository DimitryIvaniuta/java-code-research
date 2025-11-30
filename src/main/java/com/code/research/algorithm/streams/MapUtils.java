package com.code.research.algorithm.streams;

import java.util.List;
import java.util.Map;

public class MapUtils {

    /**
     * Filters the given map’s entries by value, keeping only those
     * whose value is greater than the specified threshold, and
     * returns a list of the corresponding keys.
     *
     * @param map the input Map<String,Integer>
     * @param threshold the value threshold (exclusive)
     * @return a List<String> of keys whose values exceed the threshold
     */
    public static List<String> filterKeysByValueThreshold(
            Map<String, Integer> map,
            int threshold
    ) {
        return map.entrySet().stream().filter(
                entry -> entry.getValue() > threshold
        ).map(Map.Entry::getKey).toList();
    }

    // Example usage
    public static void main(String[] args) {
        Map<String, Integer> scores = Map.of(
                "alice", 42,
                "bob",   17,
                "carol", 58,
                "dave",  30
        );

        List<String> highScorers = filterKeysByValueThreshold(scores, 30);
        System.out.println(highScorers);
        // prints: [alice, carol]
    }
}
