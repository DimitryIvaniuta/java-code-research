package com.code.research.hash.digitstatistics;

import java.util.Map;
import java.util.stream.Collectors;

public class DigitStatistics {

    private DigitStatistics() {
        //
    }

    public static <T> DigitStatisticsLongRecord computeDigits(T number) {
        String numberStr = String.valueOf(number);
        int result = numberStr.chars().map(c -> c - '0').sum();
        Map<Integer, Long> countMap = numberStr.chars()
                .map(c -> c - '0').boxed()
                .collect(Collectors.groupingBy(e->e, Collectors.counting()));

        return new DigitStatisticsLongRecord(result, countMap);
    }


    /**
     * Computes the sum of all digits in the given number and builds a frequency map
     * where the key is a digit and the value is the number of times it appears.
     *
     * <p>This method converts the number to its string representation, then uses the Java Stream API
     * to process each character, convert it to a digit, and compute both the sum and frequency counts.
     *
     * @param number the number to process.
     * @return a DigitStatistics record containing the sum of digits and the frequency map.
     */
    public static DigitStatisticsRecord<Integer> computeDigitStatistics(int number) {
        // Convert the number to a string.
        String numStr = Integer.toString(number);

        // Compute the sum of digits using stream operations.
        int sum = numStr.chars()
                .map(c -> c - '0') // Convert char code to digit.
                .sum();

        // Build a frequency map: key is the digit, value is the count.
        Map<Integer, Integer> frequencyMap = numStr.chars()
                .map(c -> c - '0')
                .boxed()
                .collect(Collectors.groupingBy(
                        digit -> digit,
                        Collectors.summingInt(d -> 1)
                ));

        return new DigitStatisticsRecord<>(sum, frequencyMap);
    }

}

