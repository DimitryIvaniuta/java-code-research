package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class BinarySearchExample {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(List.of(10, 20, 30, 40, 50));

        // Ensure the list is sorted (it is already in this example)
        int index = Collections.binarySearch(numbers, 30);
        log.info("Index of 30: {} ", index);

        // Using a custom comparator: search in descending order
        List<Integer> descending = new ArrayList<>(List.of(50, 40, 30, 20, 10));
        int indexDesc = Collections.binarySearch(descending, 30, Comparator.reverseOrder());
        log.info("Index of 30 in descending list: {}", indexDesc);
    }
}
