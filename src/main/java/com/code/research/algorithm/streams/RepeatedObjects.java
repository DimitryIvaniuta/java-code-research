package com.code.research.algorithm.streams;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RepeatedObjects {

    /**
     * Returns a list of fruits that appear more than once in the input,
     * with no duplicates in the result.
     *
     * @param fruits the input List<String> of fruit names
     * @return a List<String> of fruits that had frequency > 1, in encounter order
     */
    public static List<String> selectRepeatedFruits(List<String> fruits) {
        Map<String, Long> freq = fruits.stream()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );

        return fruits.stream()
                .distinct()
                .filter(f -> freq.getOrDefault(f, 0L) > 1)
                .toList();
    }
    // Example usage
    public static void main(String[] args) {
        List<String> basket = List.of(
                "apple", "banana", "apple", "strawberry", "orange",
                "banana", "kiwi",  "apple", "kiwi"
        );

        List<String> repeated = selectRepeatedFruits(basket);
        System.out.printf("Fruits Repeated: %s", repeated);
    }
}
