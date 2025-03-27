package com.code.research.datastructures.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Utility class that provides a method to count the number of distinct elements
 * in every contiguous subarray (window) of a specified size in an array.
 */
@Slf4j
public class CountDistinctElementsInWindow {

    /**
     * Counts the number of distinct elements in every window of size k in the given array.
     *
     * @param arr the input array of integers
     * @param k   the size of the sliding window; must be greater than 0 and less than or equal to the length of arr
     * @return a list where each element represents the number of distinct integers in the corresponding window
     * @throws IllegalArgumentException if arr is null or if k is not within a valid range
     */
    public static List<Integer> countDistinctInWindows(int[] arr, int k) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        if (k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Window size k must be between 1 and the length of the array.");
        }

        List<Integer> distinctCounts = new ArrayList<>();
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // Process the first window
        for (int i = 0; i < k; i++) {
            frequencyMap.put(arr[i], frequencyMap.getOrDefault(arr[i], 0) + 1);
        }

        Map<Integer, Integer> frequencyMapMerged = new HashMap<>();
        IntStream.range(0, k).forEach(el->
                frequencyMapMerged.merge(arr[el], 1, (key, v) -> {
                    log.info("Key/Value: {} :: {}", key, v);
                    return v + 1;
                })
        );
        log.info("Frequency Map:{}", frequencyMap);
        log.info("Frequency Map Merged:{}", frequencyMapMerged);

        distinctCounts.add(frequencyMap.size());

        // Slide the window through the rest of the array
        for (int i = k; i < arr.length; i++) {
            // Remove the element exiting the window.
            int elementToRemove = arr[i - k];
            int count = frequencyMap.get(elementToRemove);
            if (count == 1) {
                frequencyMap.remove(elementToRemove);
            } else {
                frequencyMap.put(elementToRemove, count - 1);
            }

            // Add the new element entering the window.
            int elementToAdd = arr[i];
            frequencyMap.put(elementToAdd, frequencyMap.getOrDefault(elementToAdd, 0) + 1);

            // Record the number of distinct elements in the current window.
            distinctCounts.add(frequencyMap.size());
        }

        return distinctCounts;
    }

    /**
     * Main method demonstrating counting distinct elements in every window.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int[] arr = {1, 2, 1, 3, 4, 2, 3, 1};
        int windowSize = 4;
        List<Integer> result = countDistinctInWindows(arr, windowSize);

        log.info("Input Array: {}", Arrays.toString(arr));
        log.info("Window Size: {}", windowSize);
        log.info("Distinct counts in each window: {}", result);
        // Expected Output: [3, 4, 4, 3]
    }
}
