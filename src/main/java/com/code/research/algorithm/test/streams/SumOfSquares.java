package com.code.research.algorithm.test.streams;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SumOfSquares {

    public static int sumOfSquaresEven(List<Integer> nums) {
        return nums.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * n)
                .sum();
    }

    public static void main(String[] args) {
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
        int result = sumOfSquaresEven(nums);
        log.info("Sum of squares of evens: {}", result);
        // 2^2 + 4^2 + 6^2 = 4 + 16 + 36 = 56
    }
}
