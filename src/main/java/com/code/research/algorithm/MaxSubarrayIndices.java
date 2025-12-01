package com.code.research.algorithm;

public final class MaxSubarrayIndices {
    // Return [startIndex, endIndex] of the contiguous subarray with the maximum sum (Kadane with indices)
    public static int[] maxSubarrayRange(int[] a) {
        int bestSum = a[0];                 // best sum found so far
        int curSum = a[0];                 // current running sum
        int bestL = 0, bestR = 0;           // best range [L..R]
        int curL = 0;                      // start index of current running sum

        for (int i = 1; i < a.length; i++) {
            // If starting fresh at i is better than extending, reset curSum/curL
            if (a[i] > curSum + a[i]) {
                curSum = a[i];
                curL = i;
            } else {
                curSum += a[i];
            }

            // Update global best if current beats it
            if (curSum > bestSum) {
                bestSum = curSum;
                bestL = curL;
                bestR = i;
            }
        }
        return new int[]{bestL, bestR};     // indices (inclusive)
    }

    // Optional helper: also return the sum
    public static int maxSubarraySum(int[] a) {
        int best = a[0], cur = a[0];
        for (int i = 1; i < a.length; i++) {
            cur = Math.max(a[i], cur + a[i]);
            best = Math.max(best, cur);
        }
        return best;
    }

    // tiny demo
    public static void main(String[] args) {
        int[] a1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] r1 = maxSubarrayRange(a1);
        System.out.printf("[%d,%d], sum=%d%n", r1[0], r1[1],
                java.util.Arrays.stream(a1, r1[0], r1[1] + 1).sum()); // [3,6], sum=6

        int[] a2 = {5, 4, -1, 7, 8};
        int[] r2 = maxSubarrayRange(a2);
        System.out.printf("[%d,%d], sum=%d%n", r2[0], r2[1],
                java.util.Arrays.stream(a2, r2[0], r2[1] + 1).sum()); // [0,4], sum=23
    }
}
