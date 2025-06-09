package com.code.research.algorithm.tasks;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SecondMaxApp {

    /**
     * Finds the second-largest distinct value in the given array.
     *
     * @param arr the array of integers
     * @return the second-largest distinct element, or null if it doesn't exist
     */
    public static Integer findSecondMax(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }

        Set<Integer> seen = new HashSet<>();
        Integer max = null;
        Integer secondMax = null;

        for (int n : arr) {
            // ignore duplicates
            if (!seen.add(n)) {
                continue;
            }

            if (max == null || n > max) {
                // shift down
                secondMax = max;
                max = n;
            } else if ((secondMax == null || n > secondMax) && n < max) {
                secondMax = n;
            }
        }

        return secondMax;
    }

    public static void main(String[] args) {
        int[][] tests = {
                null,
                {},
                {5},
                {7, 7, 7},
                {3, 1, 2, 4, 5},
                {10, 5, 10, 8, 8, 2},
                {-1, -2, -3, -4},
                {Integer.MAX_VALUE, Integer.MIN_VALUE, 0}
        };

        for (int[] test : tests) {
            log.info("Input: ");
            if (test == null) {
                log.info("null");
            } else {
                log.info(java.util.Arrays.toString(test));
            }

            Integer second = findSecondMax(test);
            log.info(" -> Second max: {}}",
                    (second != null ? second : "none"));
        }
    }

}
