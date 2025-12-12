package com.code.research.algorithm;

import java.util.Arrays;

public final class ContainerWithMostWaterApp {
    // Two-pointer O(n) solution: move the shorter wall inward each step
    public static int maxArea(int[] h) {
        // i/j are wall indices; best holds max area
        int i = 0;
        int j = h.length - 1;
        int best = 0;
        while (i < j) {
            // distance between walls
            int width = j - i;
            // limiting wall height
            int height = Math.min(h[i], h[j]);
            // update answer
            best = Math.max(best, width * height);
            // drop the shorter wall; only that can improve
            if (h[i] < h[j]) i++; else j--;
        }
        return best;
    }

    public static void main(String[] args) {
        int[] a1 = {1,8,6,2,5,4,8,3,7};
        int[] a2 = {1,1};
        System.out.println("Input: " + Arrays.toString(a1) + " -> max area = " + maxArea(a1)); // 49
        System.out.println("Input: " + Arrays.toString(a2) + " -> max area = " + maxArea(a2)); // 1

        // quick extra checks
        System.out.println(maxArea(new int[]{4,3,2,1,4})); // 16
        System.out.println(maxArea(new int[]{1,2,1}));     // 2
    }
}
