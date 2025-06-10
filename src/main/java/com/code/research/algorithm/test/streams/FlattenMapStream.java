package com.code.research.algorithm.test.streams;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FlattenMapStream {

    /**
     * Flattens all the integer lists in the given map into a single list.
     *
     * @param map a Map whose values are lists of integers
     * @return a List<Integer> containing all integers from all the lists in map
     */
    public static List<Integer> flattenValues(final Map<String, List<Integer>> map){
        return map.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }

    public static void main(String[] args) {
        Map<String, List<Integer>> map = Map.of(
                "group1", List.of(1, 2, 3),
                "group2", List.of(4, 5),
                "group3", List.of(6, 7, 8, 9)
        );

        List<Integer> flattened = flattenValues(map);
        System.out.println(flattened);
        // Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

}
