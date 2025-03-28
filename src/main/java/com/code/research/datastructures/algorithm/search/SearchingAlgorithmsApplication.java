package com.code.research.datastructures.algorithm.search;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class SearchingAlgorithmsApplication {

    /**
     * Main method demonstrating the usage of linear and binary search algorithms.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Example for Linear Search:
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Edward"};
        String targetName = "Charlie";
        int indexLinear = SearchingAlgorithms.linearSearch(names, targetName);
        log.info("Linear Search: Found \"{}\" at index {}", targetName, indexLinear);

        // Example for Binary Search:
        // Note: The array must be sorted. For demonstration, we use an already sorted array.
        Integer[] numbers = {1, 3, 5, 7, 9, 11, 13, 15};
        int targetNumber = 7;
        int indexBinary = SearchingAlgorithms.binarySearch(numbers, targetNumber);
        log.info("Binary Search: Found {} at index {}", targetNumber, indexBinary);

        // Test binary search with an element not in the array.
        int notFound = SearchingAlgorithms.binarySearch(numbers, 8);
        log.info("Binary Search: Element 8 found at index " + notFound);

        // For comparison, using the standard library's binary search:
        int indexStd = Arrays.binarySearch(numbers, targetNumber);
        log.info("Standard Arrays.binarySearch: Found {} at index {}", + targetNumber, indexStd);
    }

}
