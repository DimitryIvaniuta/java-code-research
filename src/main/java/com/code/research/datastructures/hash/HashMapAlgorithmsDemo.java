package com.code.research.datastructures.hash;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Demonstrates real-world algorithms using various types of HashMaps.
 *
 * <p>This class includes methods to:
 * <ul>
 *   <li>Count word frequency using a basic HashMap.</li>
 *   <li>Sort word frequencies by key using a TreeMap.</li>
 *   <li>Sort word frequencies by descending frequency using a LinkedHashMap.</li>
 *   <li>Count word frequencies concurrently using a ConcurrentHashMap.</li>
 * </ul>
 *
 * <p>These techniques are common in text analytics, caching, and multi-threaded processing.
 */
@Slf4j
public class HashMapAlgorithmsDemo {

    /**
     * Counts the frequency of words in the given text using a basic HashMap.
     *
     * @param text the input text
     * @return a map with words as keys and their frequency counts as values
     */
    public static Map<String, Integer> countWordFrequency(String text) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : text.split("\\W+")) {
            if (!word.isEmpty()) {
                String lowerCaseWord = word.toLowerCase();
                frequencyMap.put(lowerCaseWord, frequencyMap.getOrDefault(lowerCaseWord, 0) + 1);
            }
        }
        return frequencyMap;
    }

    /**
     * Sorts the given map by keys using a TreeMap.
     *
     * @param map the input map to sort
     * @return a TreeMap sorted by keys (alphabetically)
     */
    public static Map<String, Integer> sortByKey(Map<String, Integer> map) {
        return new TreeMap<>(map);
    }

    /**
     * Sorts the given map by frequency in descending order.
     * The sorted order is maintained using a LinkedHashMap.
     *
     * @param map the input map with word frequencies
     * @return a LinkedHashMap sorted by frequency in descending order
     */
    public static Map<String, Integer> sortByFrequency(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());
        entryList.sort((e1, e2) -> {
            log.info("Compare Word Frequency: {}:{}:{}", e1, e2, (e2.getValue().compareTo(e1.getValue())));
            return e2.getValue().compareTo(e1.getValue());
        }); // descending order
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    /**
     * Concurrently counts the frequency of words in a list of texts using a ConcurrentHashMap.
     *
     * @param texts a list of text chunks to process concurrently
     * @return a map with words as keys and their frequency counts as values
     * @throws InterruptedException if thread execution is interrupted
     */
    public static Map<String, Integer> concurrentFrequencyCount(List<String> texts) throws InterruptedException {
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        List<Thread> threads = new ArrayList<>();

        for (String text : texts) {
            Thread t = new Thread(() -> {
                String[] words = text.split("\\W+");
                log.info("Words: {}", Arrays.toString(words));
                for (String word : words) {
                    if (!word.isEmpty()) {
                        String lowerCaseWord = word.toLowerCase();
                        if (StringUtils.isNotBlank(lowerCaseWord)) {
                            // The merge method atomically updates the value.
                            concurrentMap.merge(lowerCaseWord, 1, Integer::sum);
                        }
                    }
                }
            });
            threads.add(t);
            t.start();
        }

        // Wait for all threads to complete.
        for (Thread t : threads) {
            t.join();
        }
        return concurrentMap;
    }

    /**
     * Main method demonstrating the usage of various hash map types in real-world scenarios.
     *
     * @param args command-line arguments (not used)
     * @throws InterruptedException if concurrent operations are interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        String sampleText = "This is a sample text. This text is a sample text for demonstrating various hash maps. "
                + "HashMap, TreeMap, LinkedHashMap, and ConcurrentHashMap are all useful in different scenarios.";

        // 1. Basic HashMap word frequency count.
        Map<String, Integer> basicFrequency = countWordFrequency(sampleText);
        log.info("Word Frequency (HashMap): {}", basicFrequency);

        // 2. Sorted by key using TreeMap.
        Map<String, Integer> sortedByKey = sortByKey(basicFrequency);
        log.info("Word Frequency Sorted by Key (TreeMap): {}", sortedByKey);

        // 3. Sorted by frequency (descending) using LinkedHashMap.
        Map<String, Integer> sortedByFrequency = sortByFrequency(basicFrequency);
        log.info("Word Frequency Sorted by Frequency (LinkedHashMap): " + sortedByFrequency);

        // 4. Concurrent frequency count using ConcurrentHashMap.
        List<String> textChunks = Arrays.asList(
                "Concurrent programming in Java is powerful.",
                "ConcurrentHashMap is thread-safe and efficient.",
                "Using multiple threads can speed up processing."
        );
        List<String> textChunksMutable = new LinkedList<>(textChunks);
        textChunksMutable.add("Sort the word frequencies by descending frequency ");
        textChunksMutable.add("(using a LinkedHashMap to preserve the sorted order).");

        Map<String, Integer> concurrentFrequency = concurrentFrequencyCount(textChunksMutable);
        log.info("Concurrent Word Frequency (ConcurrentHashMap): " + concurrentFrequency);
    }

}
