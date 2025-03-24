package com.code.research.datastructures.arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Knapsack {

    // Returns the maximum value that can be put in a knapsack of capacity W.
    public static int knapSack(int W, int[] weights, int[] values, int n) {
        int[][] dp = new int[n + 1][W + 1];

        // Build table dp[][] in bottom-up manner.
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    dp[i][w] = 0;
                else if (weights[i - 1] <= w)
                    dp[i][w] = Math.max(values[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]);
                else
                    dp[i][w] = dp[i - 1][w];
            }
        }
        return dp[n][W];
    }

    public static void main(String[] args) {
        int[] values = {60, 100, 120};
        int[] weights = {10, 20, 30};
        int capacity = 50;
        int n = values.length;

        int maxValue = knapSack(capacity, weights, values, n);
        log.info("Maximum value achievable: {}", maxValue);
    }
}
