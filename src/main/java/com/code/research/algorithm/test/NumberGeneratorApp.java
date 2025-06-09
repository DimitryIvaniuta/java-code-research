package com.code.research.algorithm.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberGeneratorApp {

    /**
     * Generates all positive integers (without leading zeros) you can form
     * by repeating digits from the given array that are ≤ N.
     *
     * @param digits the array of digits (0–9) you can use, repetition allowed
     * @param N      the upper limit (inclusive)
     * @return a list of all generated numbers ≤ N, in ascending order
     */
    public static List<Integer> generateNumbers(int[] digits, int N) {
        List<Integer> result = new ArrayList<>();
        if (digits == null || digits.length == 0 || N < 1) {
            return result;
        }

        // Sort so we can prune branches once we exceed N
        Arrays.sort(digits);
        generateRecursive(digits, N, 0, result);

        // Optional: sort the final list if you want ascending order
        result.sort(Integer::compareTo);
        return result;
    }

    // Recursively build numbers by appending each allowed digit
    private static void generateRecursive(int[] digits,
                                          int N,
                                          int prefixValue,
                                          List<Integer> result) {
        for (int d : digits) {
            // Skip leading zero
            if (prefixValue == 0 && d == 0) {
                continue;
            }
            int newValue = prefixValue * 10 + d;
            // Prune: since digits[] is sorted, any further d will only be larger
            if (newValue > N) {
                break;
            }
            result.add(newValue);
            // Recurse to add another digit after the current newValue
            generateRecursive(digits, N, newValue, result);
        }
    }

    // Demonstration
    public static void main(String[] args) {
        int[] digits = {1, 2, 3, 4};
        int N = 100;

        List<Integer> numbers = generateNumbers(digits, N);
        System.out.println("Digits: " + Arrays.toString(digits) + ", N = " + N);
        System.out.println("Generated numbers (count=" + numbers.size() + "):");
        System.out.println(numbers);
    }

}
