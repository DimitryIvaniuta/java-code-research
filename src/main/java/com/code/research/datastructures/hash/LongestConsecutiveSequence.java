package com.code.research.datastructures.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
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
     * Returns the length of the longest consecutive sequence in the given array.
     *
     * @param nums an array of integers (unsorted)
     * @return the length of the longest consecutive sequence
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // Map to store the sequence length for each number.
        // For each processed number, the map stores the length of the sequence to which it belongs.
        Map<Integer, Integer> sequenceMap = new HashMap<>();
        int longestStreak = 0;

        for (int num : nums) {
            // If the number is already processed, skip it.
            if (sequenceMap.containsKey(num)) {
                continue;
            }
            // Get the length of consecutive sequence ending at num-1 (to the left)
            int left = sequenceMap.getOrDefault(num - 1, 0);
            // Get the length of consecutive sequence starting at num+1 (to the right)
            int right = sequenceMap.getOrDefault(num + 1, 0);
            // The current number's sequence length is the sum of left, right, and the current number itself.
            int currentStreak = left + right + 1;
            // Update the current number's sequence length in the map.
            sequenceMap.put(num, currentStreak);

            // Update the boundaries of the sequence.
            // The new sequence extends from (num - left) to (num + right).
            sequenceMap.put(num - left, currentStreak);
            sequenceMap.put(num + right, currentStreak);

            // Update the maximum sequence length found.
            longestStreak = Math.max(longestStreak, currentStreak);
        }
        return longestStreak;
    }

    /**
     * Main method to demonstrate the longest consecutive sequence solution.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        LongestConsecutiveSequence solution = new LongestConsecutiveSequence();
        int[] nums = {100, 4, 200, 10, 1, 7, 2, 3, 8, 9};
        int result = solution.longestConsecutive(nums);
        log.info("Longest consecutive sequence length: {}", result);
        // Expected output: 4
    }
}
