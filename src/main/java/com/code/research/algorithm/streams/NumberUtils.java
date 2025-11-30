package com.code.research.algorithm.streams;

import java.util.List;
import java.util.stream.IntStream;

public class NumberUtils {

    /**
     * From the given int array, keeps only even numbers,
     * divides each by 2.0, and returns them as a List<Double>.
     *
     * @param numbers the input int array
     * @return List<Double> of each even number halved
     */
    public static List<Double> halfOfEvens(int[] numbers) {
        return IntStream.of(numbers)
                .mapToDouble(n -> n / 2.0)
                .boxed()
                .toList();
    }

    // Example usage
    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 10, 15, 20, 21};
        List<Double> result = halfOfEvens(data);
        System.out.println(result);
        // prints: [1.0, 2.0, 5.0, 10.0]
    }
}
