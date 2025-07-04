package com.code.research.livecoding;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TwoSum {

    /**
     * Finds two indices i and j in the array such that nums[i] + nums[j] == target.
     *
     * @param nums   an array of integers (must not be null)
     * @param target the target sum
     * @return a two-element array [i, j] of indices with i < j
     * @throws IllegalArgumentException if no two numbers sum to the target
     * @throws NullPointerException     if nums is null
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indexByValue = new HashMap<>();
        for(int i=0; i< nums.length; i++){
            int complement = target - nums[i];
            if(indexByValue.containsKey(complement)){
                return new int[]{indexByValue.get(complement), i};
            }
            indexByValue.put(nums[i], i);
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int[] nums   = { 2, 7, 11, 15 };
        int   target = 13;
        int[] result = twoSum(nums, target);
        log.info("result: {}", result);
    }
}
