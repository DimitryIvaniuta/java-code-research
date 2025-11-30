package com.code.research.algorithm.streams;

import java.util.List;

public class SumSquares {

    /**
     * Computes the sum of squares of all integers in the given list.
     *
     * @param numbers the input List of Integer
     * @return the total sum of each number squared
     */
    public static int sumOfSquares(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(i -> i * i)
                .sum();
    }

    public static void main(String[] args) {
        List<Integer> data = List.of(1, 2, 3, 4, 5);
        int result = sumOfSquares(data);
        System.out.println("Sum of squares: " + result);
        // prints: Sum of squares: 55
    }
}
