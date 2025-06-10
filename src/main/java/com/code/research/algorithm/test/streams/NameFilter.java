package com.code.research.algorithm.test.streams;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class NameFilter {

    /**
     * Filters the input list to names starting with 'A',
     * converts them to uppercase, and collects into a new list.
     */
    public static List<String> filterAndCollect(List<String> names){
        return names.stream()
                .filter(n -> n.startsWith("A"))
                .map(String::toUpperCase)
                .toList();
    }

    // Example usage
    public static void main(String[] args) {
        List<String> names = List.of("Alice", "bob", "Andrew", "Charlie", "anna");
        List<String> result = filterAndCollect(names);
        log.info("Names: {}", result.toArray());
    }

}
