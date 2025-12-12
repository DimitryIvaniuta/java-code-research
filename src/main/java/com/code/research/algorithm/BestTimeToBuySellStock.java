package com.code.research.algorithm;

public final class BestTimeToBuySellStock {
    // Return the max profit from one buy then one sell (or 0 if none)
    public static int maxProfit(int[] prices) {
        // lowest price seen so far (best buy)
        int min = Integer.MAX_VALUE;
        // best profit so far
        int best = 0;
        // scan each day's price
        for (int p : prices) {
            // update best buy price
            min = Math.min(min, p);
            // profit if we sell today
            // keep the max profit
            best = Math.max(best, p - min);
        }
        // 0 if never profitable
        return best;
    }
    public static int maxProfit2(int[] prices) {
        // lowest price seen so far (best buy)
        int min = Integer.MAX_VALUE;
        // best profit so far
        int best = 0;
        // scan each day's price
        for (int p : prices) {
            // update best buy price
            if (p < min) {
                min = p;
            }
            // profit if we sell today
            int profit = p - min;
            // keep the max profit
            if (profit > best) {
                best = profit;
            }
        }
        return best;                   // 0 if never profitable
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 5
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}));   // 0
    }
}
