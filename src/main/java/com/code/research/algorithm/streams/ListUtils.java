package com.code.research.algorithm.streams;

import java.util.List;

public class ListUtils {


    /**
     * Flattens a nested list of strings into a single list.
     *
     * @param nestedList the input List of List of String
     * @return a flat List containing all strings in order
     */
    public static List<String> flatten(List<List<String>> nestedList) {
        return nestedList.stream().flatMap(List::stream).toList();
    }
    // Example usage
    public static void main(String[] args) {
        List<List<String>> data = List.of(
                List.of("apple", "banana"),
                List.of("cherry"),
                List.of("date", "elderberry", "fig"),
                List.of()                       // empty inner list
        );

        List<String> flat = flatten(data);
        System.out.println(flat);
        // prints: [apple, banana, cherry, date, elderberry, fig]
    }
}
