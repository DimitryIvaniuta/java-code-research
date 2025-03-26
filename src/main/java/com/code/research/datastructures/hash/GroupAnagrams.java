package com.code.research.datastructures.hash;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for grouping words by a computed "word code".
 * The word code is defined as the sum of the ASCII values of the word's characters.
 */
@Slf4j
public class GroupAnagrams {

    /**
     * Computes a numeric code for the given word by summing the ASCII values of its characters.
     *
     * @param word the input word
     * @return the computed word code as an integer
     */
    public static int computeWordCode(String word) {
        int sum = 0;
        for (char c : word.toCharArray()) {
            sum += c;
        }
        return sum;
    }

    /**
     * Main method demonstrating grouping of words based on their computed word code.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Map to store lists of words grouped by their computed word code.
        Map<Integer, List<String>> anagrams = new HashMap<>();
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};

        // Process each word.
        for (String word : words) {
            int code = computeWordCode(word);
            log.info("Word: '{}' has code: {}", word, code);

            // Add the word to the list for this code, creating a new list if necessary.
            anagrams.computeIfAbsent(code, key -> new ArrayList<>()).add(word);
        }

        log.info("Grouped words by code: {}", anagrams.values());
    }

}
