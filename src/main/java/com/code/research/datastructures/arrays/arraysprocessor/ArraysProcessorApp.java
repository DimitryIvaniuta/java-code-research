package com.code.research.datastructures.arrays.arraysprocessor;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class ArraysProcessorApp {

    /**
     * Main method demonstrating the usage of various array processing methods.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Array construction for demonstration.
        Integer[] arr1 = {1, 3, 5, 7, 9};
        Integer[] arr2 = {2, 4, 6, 8, 10};

        // Demonstrate findMin and findMax.
        log.info("Minimum of arr1: {}", ArraysProcessor.findMin(arr1));
        log.info("Maximum of arr1: {}", ArraysProcessor.findMax(arr1));

        // Demonstrate reverse.
        Integer[] arr3 = {10, 20, 30, 40, 50};
        log.info("Original arr3: " + Arrays.toString(arr3));
        ArraysProcessor.reverse(arr3);
        log.info("Reversed arr3: " + Arrays.toString(arr3));

        // Demonstrate iterative binary search.
        int index = ArraysProcessor.binarySearchIterative(arr1, 7);
        log.info("Index of 7 in arr1 (iterative binary search): " + index);

        // Demonstrate recursive binary search.
        index = ArraysProcessor.binarySearchRecursive(arr1, 7, 0, arr1.length - 1);
        log.info("Index of 7 in arr1 (recursive binary search): " + index);

        // Demonstrate mergeSortedArrays.
        Integer[] merged = ArraysProcessor.mergeSortedArrays(arr1, arr2);
        log.info("Merged sorted arrays: " + Arrays.toString(merged));

        // Demonstrate removeDuplicates.
        Integer[] dupArray = {1, 1, 2, 2, 2, 3, 4, 4, 5};
        int newLength = ArraysProcessor.removeDuplicates(dupArray);
        log.info("Array after removing duplicates: ");
        for (int i = 0; i < newLength; i++) {
            log.info("{} ", dupArray[i]);
        }
        log.info("");

        // Demonstrate frequencyCount.
        Integer[] freqArray = {1, 2, 2, 3, 3, 3, 4, 4, 4, 4};
        Map<Integer, Integer> frequencyMap = ArraysProcessor.frequencyCount(freqArray);
        log.info("Frequency Map: " + frequencyMap);

        // Demonstrate sum on an int array.
        int[] intArray = {1, 2, 3, 4, 5};
        log.info("Sum of intArray: {}", ArraysProcessor.sum(intArray));
    }

}
