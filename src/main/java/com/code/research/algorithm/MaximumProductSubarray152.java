package com.code.research.algorithm;

public final class MaximumProductSubarray152 {
    // Return the maximum product over all contiguous subarrays
    public static int maxProduct(int[] nums) {
        // best product seen so far
        int best = nums[0];
        // max product ending at current index
        int maxHere = nums[0];
        // min product ending at current index (tracks negatives)
        int minHere = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // current number
            int x = nums[i];
            // negative flips roles of max/min
            if (x < 0) {
                int tmp = maxHere;
                maxHere = minHere;
                minHere = tmp;
            }
            // either start new subarray at x or extend
            maxHere = Math.max(x, maxHere * x);
            // track smallest (most negative) for future flips
            minHere = Math.min(x, minHere * x);
            // update global best
            best = Math.max(best, maxHere);
        }
        // maximum product subarray
        return best;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(maxProduct(new int[]{2, 3, -2, 4}));  // 6
        System.out.println(maxProduct(new int[]{-2, 0, -1}));   // 0
        System.out.println(maxProduct(new int[]{-2, 3, -4}));   // 24
    }
}
