package com.code.research.hash.digitstatistics;

import java.util.Map;

/**
 * DigitStatisticsRecord is a generic record that holds the overall sum of digits
 * (as an int) and a frequency map where each key is a digit (0-9) and the value is of type T.
 *
 * @param <T> the numeric type used for frequency counts (e.g., Integer, Long, Float, Double)
 * @param sum the overall sum of the digits.
 * @param frequencyMap a map from digit to its frequency count.
 */
public record DigitStatisticsRecord<T>(Integer sum, Map<Integer, T> frequencyMap) {

    @Override
    public String toString() {
        return "DigitStatisticsRecord{" +
                "sum=" + sum +
                ", frequencyMap=" + frequencyMap +
                '}';
    }

}
