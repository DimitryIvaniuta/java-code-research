package com.code.research.hash.digitstatistics;

import java.util.Map;

/**
 * A functional interface for counting the frequency of digits in an integer.
 */
@FunctionalInterface
public interface FrequencyCounter {
    /**
     * Counts the frequency of each digit in the given number.
     *
     * @param number the input number
     * @return a Map where keys are digits (0-9) and values are their frequency counts
     */
    Map<Integer, Integer> countFrequency(int number);
}
