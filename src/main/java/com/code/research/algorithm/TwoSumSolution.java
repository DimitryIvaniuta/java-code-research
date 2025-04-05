package com.code.research.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * TwoSumSolution provides a method to find indices of two numbers in an array
 * that sum up to a given target.
 *
 * <p>This class implements an O(n) solution using a HashMap to store the
 * elements and their indices as we iterate over the array.
 */
public class TwoSumSolution {

    /**
     * Returns the indices of the two numbers in the array such that they add up to target.
     * Assumes exactly one valid solution exists.
     *
     * <p>Algorithm (HashMap):
     * <ol>
     *   <li>Create a HashMap to store array values and their indices.</li>
     *   <li>For each element nums[i], compute complement = target - nums[i].</li>
     *   <li>If the complement exists in the HashMap, return the indices (map.get(complement), i).</li>
     *   <li>Otherwise, put nums[i] and i in the map and continue.</li>
     * </ol>
     *
     * @param nums   the input array of integers.
     * @param target the target sum.
     * @return an array of two indices representing the solution.
     * @throws IllegalArgumentException if no solution is found (though the problem states exactly one solution exists).
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        // map: value -> index
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{ map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        // According to the problem statement, there is always exactly one solution.
        throw new IllegalArgumentException("No two sum solution");
    }

}
