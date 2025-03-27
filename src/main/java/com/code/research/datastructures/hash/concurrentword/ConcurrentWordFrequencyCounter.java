package com.code.research.datastructures.hash.concurrentword;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ConcurrentWordFrequencyCounter provides a thread-safe mechanism to count word frequencies
 * across multiple text chunks processed concurrently.
 *
 * <p>This class leverages a ConcurrentHashMap and an ExecutorService wrapped in an AutoCloseable helper
 * to ensure proper resource management using try-with-resources.
 */
@Slf4j
public class ConcurrentWordFrequencyCounter {

    /**
     * Counts the frequencies of words in the given list of text chunks concurrently.
     *
     * @param texts a list of text chunks (e.g., from files or streams)
     * @return a ConcurrentHashMap mapping words (in lowercase) to their frequency counts
     */
    public static Map<String, Integer> countWordFrequencies(List<String> texts) {
        ConcurrentHashMap<String, Integer> frequencyMap = new ConcurrentHashMap<>();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();

        // Wrap the ExecutorService in our AutoCloseable helper to use try-with-resources.
        try (ExecutorServiceWrapper executorWrapper = new ExecutorServiceWrapper(
                Executors.newFixedThreadPool(numberOfThreads))) {
            ExecutorService executorService = executorWrapper.getExecutorService();

            // Process each text chunk concurrently.
            for (String text : texts) {
                executorService.submit(() -> {
                    // Split the text into words using non-word characters as delimiters.
                    String[] words = text.split("\\W+");
                    for (String word : words) {
                        if (!word.isEmpty()) {
                            String lowerCaseWord = word.toLowerCase();
                            // Atomically update the frequency count for the word.
                            frequencyMap.merge(lowerCaseWord, 1, Integer::sum);
                        }
                    }
                });
            }
        } // The ExecutorService is automatically shut down here.

        return frequencyMap;
    }

    /**
     * Main method demonstrating concurrent word frequency counting.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        List<String> texts = List.of(
                "This is a test. This test is only a test.",
                "Concurrent programming in Java is powerful.",
                "Test the concurrency using multiple threads and see the result.",
                "Another test case to check the concurrent frequency counter."
        );

        Map<String, Integer> wordFrequencies = countWordFrequencies(texts);

        log.info("Word Frequencies:");
        wordFrequencies.forEach((word, count) -> log.info(word + " -> " + count));
    }

}
