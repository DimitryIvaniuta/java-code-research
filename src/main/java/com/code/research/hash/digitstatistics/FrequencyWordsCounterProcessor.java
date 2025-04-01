package com.code.research.hash.digitstatistics;

import java.util.HashMap;
import java.util.Map;

public class FrequencyWordsCounterProcessor {

    private FrequencyWordsCounterProcessor() {
        //
    }

    /**
     * Computes a frequency map using the merge() method.
     * Each word is merged with an initial value of 1; if the key already exists,
     * the remapping function adds 1 to the existing count.
     *
     * @param words the array of words.
     * @return a map where the key is a word and the value is its frequency.
     */
    public static Map<String, Integer> frequencyUsingMerge(String[] words) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            freqMap.merge(word, 1, Integer::sum);
        }
        return freqMap;
    }

    /**
     * Computes a frequency map using computeIfAbsent() and then updating the value.
     * This method first ensures a key is present (defaulting to 0) and then increments it.
     *
     * @param words the array of words.
     * @return a map where the key is a word and the value is its frequency.
     */
    public static Map<String, Integer> frequencyUsingComputeIfAbsent(String[] words) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            // Ensure a value is present (0 if absent) then update.
            freqMap.computeIfAbsent(word, k -> 0);
            freqMap.put(word, freqMap.get(word) + 1);
        }
        return freqMap;
    }

    /**
     * Computes a frequency map using the putOrDefault() method.
     * For each word, it gets the current count (defaulting to 0) and then puts the incremented count.
     *
     * @param words the array of words.
     * @return a map where the key is a word and the value is its frequency.
     */
    public static Map<String, Integer> frequencyUsingPutOrDefault(String[] words) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            int count = freqMap.getOrDefault(word, 0);
            freqMap.put(word, count + 1);
        }
        return freqMap;
    }

    /**
     * Computes a frequency map using the compute() method.
     * The compute method updates the mapping for a key by applying a remapping function.
     *
     * @param words the array of words.
     * @return a map where the key is a word and the value is its frequency.
     */
    public static Map<String, Integer> frequencyUsingCompute(String[] words) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            freqMap.compute(word, (k, v) -> (v == null) ? 1 : v + 1);
        }
        return freqMap;
    }

}
