package com.code.research.algorithm.tasks;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class CountNumbersApp {

    /**
     * Counts how many positive integers (without leading zeros)
     * you can form using the given set of digits (repetition allowed)
     * that are ≤ N.
     *
     * @param digits an array of non-negative digits (0–9)
     * @param N       the upper limit (inclusive)
     * @return the count of numbers ≤ N you can form
     */
    public static long countNumbers(int[] digits, int N) {
        if (digits == null || digits.length == 0 || N <= 0) {
            return 0;
        }

        // Sort digits for easier “<” comparisons
        Arrays.sort(digits);

        String s = String.valueOf(N);
        int L = s.length();
        int D = digits.length;
        long total = 0;

        // 1) Count numbers with length < L
        //    For length = 1, you can use any digit except you normally exclude leading zeros.
        //    But we allow "0" itself as a valid single-digit number only if 0 <= digits.
        //    For length >= 2, the first digit cannot be 0.
        boolean hasZero = Arrays.binarySearch(digits, 0) >= 0;
        int nonZeroCount = hasZero ? D - 1 : D;

        // length = 1
        total += D;
        // length = 2, 3, ..., L-1
        for (int len = 2; len < L; len++) {
            total += nonZeroCount * pow(D, len - 1);
        }

        // 2) Count numbers with length = L by scanning each position
        //    We do a digit-DP-like greedy count:
        for (int i = 0; i < L; i++) {
            int nd = s.charAt(i) - '0';
            // Count how many choices at this position are strictly less than nd
            for (int d : digits) {
                if (d >= nd) {
                    break;
                }
                // Skip leading zero for multi-digit numbers
                if (i == 0 && L > 1 && d == 0) {
                    continue;
                }
                total += pow(D, L - i - 1);
            }
            // If the exact digit nd is not in our set, we cannot match N exactly beyond this point
            if (Arrays.binarySearch(digits, nd) < 0) {
                return total;
            }
            // Otherwise, we “lock in” nd and move to the next position
        }

        // If we reach here, every digit of N is in digits → include N itself
        return total + 1;
    }

    // Helper for integer exponentiation
    private static long pow(int base, int exp) {
        long result = 1;
        while (exp-- > 0) {
            result *= base;
        }
        return result;
    }

    // Quick demonstration
    public static void main(String[] args) {
        int[][] digitSets = {
                {1, 3, 5, 7},
                {0, 1, 2},
                {2, 4, 6, 8},
        };
        int[] limits = {100, 25, 500};

        for (int i = 0; i < digitSets.length; i++) {
            System.out.printf("Digits: %s, N = %d → Count = %d%n",
                    Arrays.toString(digitSets[i]),
                    limits[i],
                    countNumbers(digitSets[i], limits[i]));
        }
        // Example: {1,3,5,7}, N=100 → should print 20
    }
}
