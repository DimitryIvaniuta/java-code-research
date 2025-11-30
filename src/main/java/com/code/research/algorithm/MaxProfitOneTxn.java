package com.code.research.algorithm;

public class MaxProfitOneTxn {
    // Returns max profit from exactly one buy and one sell (buy before sell).
    public static int maxProfit(int[] prices) {
        int minProfit = Integer.MAX_VALUE;
        int bestProfit = 0;
        for (int p : prices) {
            if (p < minProfit) {
                minProfit = p;
            }
            int profit = p - minProfit;
            if (profit > bestProfit) {
                bestProfit = profit;
            }
        }
        return bestProfit;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 5 (buy 1, sell 6)
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}));   // 0 (no profit)
    }
}
