package com.code.research.datastructures.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides a method to find the k most frequent elements in an array.
 *
 * <p>This solution demonstrates the use of various Map implementations:
 * <ul>
 *   <li>A {@code HashMap} to count the frequency of each element.</li>
 *   <li>A {@code LinkedHashMap} to maintain insertion order after sorting by frequency.</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * Input:  nums = [1,1,1,2,2,3], k = 2
 * Output: [1, 2]
 * </pre>
 */
@Slf4j
public class TopKFrequentElements {

    /**
     * Returns the k most frequent elements from the given array.
     *
     * @param nums the input array of integers
     * @param k    the number of top frequent elements to return
     * @return a list of integers representing the k most frequent elements
     */
    public static List<Integer> topKFrequent(int[] nums, int k) {
        // Count the frequency of each element using a HashMap.
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // Create a list of the frequency map's entries.
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(frequencyMap.entrySet());

        // Sort the entries in descending order based on frequency.
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Optionally, store the sorted entries in a LinkedHashMap to preserve the sorted order.
        Map<Integer, Integer> sortedByFrequency = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : entryList) {
            sortedByFrequency.put(entry.getKey(), entry.getValue());
        }

        // Extract the top k keys from the sorted map.
        List<Integer> topKElements = new ArrayList<>();
        int count = 0;
        for (Integer key : sortedByFrequency.keySet()) {
            if (count < k) {
                topKElements.add(key);
                count++;
            } else {
                break;
            }
        }
        return topKElements;
    }

    /**
     * Main method to demonstrate the topKFrequent functionality.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        List<Integer> result = topKFrequent(nums, k);
        log.info("Top {} frequent elements: {}", k, result);
    }

}
