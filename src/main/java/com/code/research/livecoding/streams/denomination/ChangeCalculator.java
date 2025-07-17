package com.code.research.livecoding.streams.denomination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeCalculator {
    // Denominations in descending order
    private static final List<Integer> DENOMINATIONS =
            Arrays.asList(100, 50, 20, 10, 5, 1);

    /**
     * Given the price and the amount paid, returns a list of bills/coins
     * (as integers) that sum to the change, using a greedy algorithm.
     *
     * @param price the amount due
     * @param paid  the amount given by the customer
     * @return list of denominations to give as change
     * @throws IllegalArgumentException if paid < price
     */
    public static List<Integer> calculateChange(int price, int paid) {
        if (paid < price) {
            throw new IllegalArgumentException("Paid amount must be at least the price.");
        }
        int change = paid - price;
        List<Integer> result = new ArrayList<>();

        for (int denom : DENOMINATIONS) {
            while (change >= denom) {
                change -= denom;
                result.add(denom);
            }
        }
        return result;
    }

    /**
     * Formats a list of denominations into a string like "$20 + $20 + $5 + $1"
     */
    public static String formatChange(List<Integer> coins) {
        return coins.stream()
                .map(d -> "$" + d)
                .collect(Collectors.joining(" + "));
    }

    public static void main(String[] args) {
        int price = 154;
        int paid  = 200;

        List<Integer> change = calculateChange(price, paid);
        System.out.println("Change amount: $" + (paid - price));
        System.out.println("Breakdown: " + formatChange(change));
        // prints:
        // Change amount: $46
        // Breakdown: $20 + $20 + $5 + $1
    }
}
