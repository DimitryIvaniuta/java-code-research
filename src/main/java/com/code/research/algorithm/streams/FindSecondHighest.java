package com.code.research.algorithm.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FindSecondHighest {

    /**
     * Finds the second-highest element in the list.
     *
     * @param numbers the input List<Integer>
     * @return an Optional containing the second-highest number if present;
     *         otherwise, an empty Optional
     */
    public static Optional<Integer> findSecondHighest(List<Integer> numbers) {
        return numbers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();
    }

    public static void main(String[] args) {
        List<Integer> data1 = List.of(10, 5, 20, 20, 1);
        List<Integer> data2 = List.of(42);             // too few elements
        List<Integer> data3 = List.of(42, 4, 53, 23, 65, 52);

        Optional<Integer> second1 = findSecondHighest(data1);
        Optional<Integer> second2 = findSecondHighest(data2);
        Optional<Integer> second3 = findSecondHighest(data3);

        second1.ifPresentOrElse(
                val -> System.out.println("Second highest (data1): " + val),
                ()  -> System.out.println("Second highest (data1): not available")
        );

        second2.ifPresentOrElse(
                val -> System.out.println("Second highest (data2): " + val),
                ()  -> System.out.println("Second highest (data2): not available")
        );
        second3.ifPresentOrElse(
                v -> System.out.printf("Second Value: %s", v),
                () -> System.out.println("Second Value: not available")
        );
        // Expected output:
        // Second highest (data1): 10
        // Second highest (data2): not available
    }
}
