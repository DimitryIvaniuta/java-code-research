package com.code.research.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class UnmodifiableCollectionsExample {

    public static void main(String[] args) {
        List<String> immutableList = List.of("Alice", "Bob", "Charlie");
        Set<Integer> immutableSet = Set.of(1, 2, 3, 4);
        Map<String, Integer> immutableMap = Map.of("A", 1, "B", 2, "C", 3);

        log.info("Immutable List: {}", immutableList);
        log.info("Immutable Set: {}", immutableSet);
        log.info("Immutable Map: {}", immutableMap);

        // Attempting to modify these will throw UnsupportedOperationException
        // immutableList.add("David");  // Uncommenting will cause exception.
    }

}
