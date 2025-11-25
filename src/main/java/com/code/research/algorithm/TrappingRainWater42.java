package com.code.research.algorithm;

public final class TrappingRainWater42 {
    // Two-pointer O(n)/O(1): water at i = min(maxLeft, maxRight) - height[i] if positive
    public static int trap(int[] height) {
        // pointers from both ends
        int l = 0;
        int r = height.length - 1;
        int leftMax = 0;
        // best walls seen so far from left/right
        int rightMax = 0;
        // total trapped water
        int water = 0;

        while (l < r) {
            // left side is the bottleneck
            if (height[l] <= height[r]) {
                // update left wall
                leftMax = Math.max(leftMax, height[l]);
                // add water above bar l (0 if negative)
                water += leftMax - height[l];
                // move inward
                l++;
            } else {
                // update right wall
                rightMax = Math.max(rightMax, height[r]);
                // add water above bar r
                water += rightMax - height[r];
                // move inward
                r--;
            }
        }
        return water;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1})); // 6
        System.out.println(trap(new int[]{4,2,0,3,2,5}));             // 9
    }
}
