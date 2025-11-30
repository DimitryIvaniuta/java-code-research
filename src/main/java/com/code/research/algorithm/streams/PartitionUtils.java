package com.code.research.algorithm.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PartitionUtils {

    /**
     * Partitions the given list of integers into evens and odds.
     *
     * @param numbers the input List<Integer>
     * @return a Map<Boolean, List<Integer>> where:
     * - key `true` maps to the list of even numbers
     * - key `false` maps to the list of odd numbers
     */
    public static Map<Boolean, List<Integer>> partitionEvenOdd(List<Integer> numbers) {
        return numbers.stream().collect(
                Collectors.partitioningBy(n -> n % 2 == 0)

        );
    }

    public static void main(String[] args) {
        List<Integer> data = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Map<Boolean, List<Integer>> partitioned = partitionEvenOdd(data);
        System.out.println("Evens: " + partitioned.get(true));   // [2, 4, 6, 8, 10]
        System.out.println("Odds:  " + partitioned.get(false));  // [1, 3, 5, 7, 9]
    }
}
