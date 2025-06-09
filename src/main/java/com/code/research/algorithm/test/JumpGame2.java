package com.code.research.algorithm.test;

public class JumpGame2 {

    public boolean canJump(int[] nums) {
        int furthestReach = 0;
        int lastIndex = nums.length - 1;

        for(int i = 0; i <= furthestReach && furthestReach < lastIndex; i++ ) {
            furthestReach = Math.max(furthestReach, i + nums[i]);
        }
        return furthestReach >= lastIndex;
    }
}
