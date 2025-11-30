package com.code.research.algorithm.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelSumUtils {

    /**
     * Computes the sum of all floats in the given list using a parallel stream.
     *
     * @param numbers the input List<Float>
     * @return the total sum as a double
     */
    public static double parallelSum(List<Float> numbers) {
        return numbers.parallelStream()
                .mapToDouble(Float::doubleValue)
                .sum();
    }

    // Alternate version returning a float using a parallel reduce with combiner:
    public static float parallelSumFloat(List<Float> numbers) {
        return numbers.parallelStream().reduce(0.0F, Float::sum, Float::sum);
    }

    public static float parallelSumFloat2(List<Float> numbers) {
        return numbers.parallelStream().reduce(0.0F, Float::sum);
    }

    public static void main(String[] args) {
        // Simulate a large list of floats
        List<Float> data = IntStream.rangeClosed(1, 1_000_000)
                .mapToObj(i -> i * 0.5f)
                .collect(Collectors.toList());

        double sumAsDouble = parallelSum(data);
        System.out.printf("Parallel sum (double): %.2f%n", sumAsDouble);

        float sumAsFloat = parallelSumFloat(data);
        System.out.printf("Parallel sum (float): %.2f%n", sumAsFloat);
        System.out.printf("Parallel sum2 (float): %.2f%n", parallelSumFloat2(data));
    }
}
