package com.code.research.algorithm.streams;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    /**
     * Joins the given list of strings into a single comma-separated string.
     * If the list is empty, returns an empty string.
     *
     * @param items the list of strings to join
     * @return a single string with items separated by commas
     */
    public static String joinWithCommas(List<String> items) {
        return items.stream()
                .map(String::toUpperCase)
                .collect(
                Collectors.joining(", ")
        );
    }
    public static void main(String[] args) {
        List<String> names = List.of("Alice", "Bob", "Carol", "Dave");
        String result = joinWithCommas(names);
        System.out.println(result);
        // prints: Alice,Bob,Carol,Dave
    }
}
