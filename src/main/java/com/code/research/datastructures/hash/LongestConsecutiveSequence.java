package com.code.research.datastructures.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides a solution to the "Longest Consecutive Sequence" problem using a HashMap.
 * <p>
 * The algorithm runs in O(n) time by using a HashMap to store, for each number, the length
 * of the consecutive sequence it is part of. For a new number, we determine the length of
 * the sequence to its left and right (if they exist), then update the boundaries of the sequence.
 * </p>
 *
 * <p>Example:
 * <pre>
 * Input: [100, 4, 200, 1, 3, 2]
 * Output: 4
 * (The longest consecutive sequence is [1, 2, 3, 4])
 * </pre>
 * </p>
 */
@Slf4j
public class LongestConsecutiveSequence {

    /**
     * Finds and returns the longest consecutive sequence from the given array.
     *
     * @param nums an array of unsorted integers
     * @return a list of integers representing the longest consecutive sequence;
     *         if the array is empty, returns an empty list.
     */
    public static List<Integer> getLongestConsecutiveSequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }

        // Map to store the sequence length for each number.
        Map<Integer, Integer> sequenceMap = new HashMap<>();
        int maxStreak = 0;
        int maxStart = 0; // The starting value of the longest sequence.

        for (int num : nums) {
            // Skip if the number is already processed.
            if (sequenceMap.containsKey(num)) {
                continue;
            }

            // Get the lengths of consecutive sequences adjacent to num.
            int left = sequenceMap.getOrDefault(num - 1, 0);
            int right = sequenceMap.getOrDefault(num + 1, 0);

            // The current sequence length including num.
            int currentStreak = left + right + 1;

            // Update the map for the current number.
            sequenceMap.put(num, currentStreak);
            // Update the boundary values of the sequence.
            sequenceMap.put(num - left, currentStreak);
            sequenceMap.put(num + right, currentStreak);

            // Update maximum sequence if needed.
            if (currentStreak > maxStreak) {
                maxStreak = currentStreak;
                maxStart = num - left; // Starting number of the current sequence.
            }
        }

        // Reconstruct the longest consecutive sequence.
        List<Integer> longestSequence = new ArrayList<>();
        for (int i = 0; i < maxStreak; i++) {
            longestSequence.add(maxStart + i);
        }
        return longestSequence;
    }


    /**
     * Main method to demonstrate the longest consecutive sequence solution.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int[] nums = {100, 4, 200, 10, 1, 7, 2, 3, 8, 9, 11, 12};
        List<Integer> longestSequence = getLongestConsecutiveSequence(nums);
        log.info("Longest consecutive sequence: {}", longestSequence);
    }
}
