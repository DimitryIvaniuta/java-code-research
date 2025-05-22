package com.code.research.algorithm;

/**
 *  You are given an integer array nums.
 *  You are initially positioned at the array's first index,
 *  and each element in the array represents your maximum jump length at that position.
 */
public class JumpGame {

    /**
     * Determines if you can reach the last index of the array.
     *
     * @param nums non-null array of non-negative jump lengths
     * @return true if the last index is reachable, false otherwise
     */
    public static boolean canJump(int[] nums) {
        int furthestReach = 0;
        int lastIndex = nums.length - 1;

        for (int i = 0; i <= furthestReach && furthestReach < lastIndex; i++) {
            // update the furthest reachable index from position i
            furthestReach = Math.max(furthestReach, i + nums[i]);
        }
        return furthestReach >= lastIndex;
    }

}
