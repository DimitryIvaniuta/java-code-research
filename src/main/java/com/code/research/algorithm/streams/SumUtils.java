package com.code.research.algorithm.streams;

import java.util.List;

public class SumUtils {

    /**
     * Computes the sum of all String in the given list.
     *
     * @param numbers the input List of String
     * @return the total sum as an int
     */
    public static int sumList(List<String> numbers) {
        return numbers.stream().mapToInt(Integer::valueOf).sum();
    }

    // Example usage
    public static void main(String[] args) {
        List<String> data = List.of("5", "10", "15", "20", "-3");
        int total = sumList(data);
        System.out.println("Sum is: " + total);
        // prints: Sum is: 47
    }
}
