package com.code.research.algorithm;

public final class FindDuplicate287 {
    // Floyd's cycle detection: O(n) time, O(1) space, array unchanged
    public static int findDuplicate(int[] nums) {
        // Phase 1: find meeting point inside the cycle
        // moves 1 step
        int tortoise = nums[0];
        // moves 2 steps
        int hare = nums[nums[0]];
        // loop until they meet
        while (tortoise != hare) {
            // +1 step
            tortoise = nums[tortoise];
            // +2 steps
            hare = nums[nums[hare]];
        }

        // Phase 2: find cycle entry = duplicate value
        // reset one pointer to start
        tortoise = 0;
        // move both 1 step; meet at duplicate
        while (tortoise != hare) {
            tortoise = nums[tortoise];
            hare = nums[hare];
        }
        // or tortoise
        return hare;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(findDuplicate(new int[]{1, 3, 4, 2, 2})); // 2
        System.out.println(findDuplicate(new int[]{3, 1, 3, 4, 2})); // 3
        System.out.println(findDuplicate(new int[]{3, 3, 3, 3, 3})); // 3
    }
}
