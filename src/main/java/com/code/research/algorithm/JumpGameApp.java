package com.code.research.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.code.research.algorithm.JumpGame.canJump;

@Slf4j
public class JumpGameApp {

    public static void main(String[] args) {
        // Example 1
        int[] nums1 = {2, 3, 1, 1, 1, 4};
        log.info("Input1:  {}", Arrays.toString(nums1));
        log.info("Output1: {}", canJump(nums1));
        // Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.

        // Additional tests
        int[] nums2 = {3, 2, 1, 0, 4};
        log.info("\nInput2:  {}", Arrays.toString(nums2));
        log.info("Output2: {}", canJump(nums2));
        // Output: false (stuck at index 3)

        int[] nums3 = {0};
        log.info("\nInput3:  {}", Arrays.toString(nums3));
        log.info("Output3: {}", canJump(nums3));
        // Output: true (already at last index)
    }

}
