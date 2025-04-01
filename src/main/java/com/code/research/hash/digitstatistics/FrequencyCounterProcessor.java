package com.code.research.hash.digitstatistics;

import java.util.HashMap;
import java.util.Map;

/**
 * FrequencyCounterExample demonstrates four different implementations for counting digit frequencies
 * using various HashMap methods in a functional style.
 */
public class FrequencyCounterProcessor {

    /**
     * Computes the frequency map using the merge() method.
     *
     * @param number the input number
     * @return a Map where keys are digits and values are their frequency counts
     */
    public static Map<Integer, Integer> countUsingMerge(int number) {
        String numStr = Integer.toString(number);
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (char c : numStr.toCharArray()) {
            int digit = c - '0';
            freqMap.merge(digit, 1, Integer::sum);
        }
        return freqMap;
    }

    /**
     * Computes the frequency map using computeIfAbsent() followed by an update with put().
     *
     * @param number the input number
     * @return a Map where keys are digits and values are their frequency counts
     */
    public static Map<Integer, Integer> countUsingComputeIfAbsent(int number) {
        String numStr = Integer.toString(number);
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (char c : numStr.toCharArray()) {
            int digit = c - '0';
            // Ensure a default value is present, then update.
            freqMap.computeIfAbsent(digit, key -> 0);
            freqMap.put(digit, freqMap.get(digit) + 1);
        }
        return freqMap;
    }

    /**
     * Computes the frequency map using getOrDefault() and put().
     *
     * @param number the input number
     * @return a Map where keys are digits and values are their frequency counts
     */
    public static Map<Integer, Integer> countUsingPutOrDefault(int number) {
        String numStr = Integer.toString(number);
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (char c : numStr.toCharArray()) {
            int digit = c - '0';
            int count = freqMap.getOrDefault(digit, 0);
            freqMap.put(digit, count + 1);
        }
        return freqMap;
    }

    /**
     * Computes the frequency map using the compute() method.
     *
     * @param number the input number
     * @return a Map where keys are digits and values are their frequency counts
     */
    public static Map<Integer, Integer> countUsingCompute(int number) {
        String numStr = Integer.toString(number);
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (char c : numStr.toCharArray()) {
            int digit = c - '0';
            freqMap.compute(digit, (key, value) -> (value == null) ? 1 : value + 1);
        }
        return freqMap;
    }

}
