package com.code.research.algorithm.test.streams;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.OptionalInt;

@Slf4j
public class LongestStringLength {

    /**
     * Finds the length of the longest word in the list.
     *
     * @param words the input list of strings
     * @return an OptionalInt containing the maximum length, or empty if the list is empty
     */
    public static OptionalInt longestStringLength(List<String> words){
        return words.stream()
                .mapToInt(String::length)
                .max();
    }

    /**
     * Convenience method that returns 0 when the list is empty.
     *
     * @param words the input list of strings
     * @return the maximum length, or 0 if the list is empty
     */
    public static int longestStringLengthOrZero(List<String> words){
        return longestStringLength(words)
                .orElse(0);
    }

    public static void main(String[] args) {
        List<String> words1 = List.of("apple", "banana", "cherry", "date");
        OptionalInt maxLen1 = longestStringLength(words1);
        log.info("Longest length: {}",
                (maxLen1.isPresent() ? maxLen1.getAsInt() : "none"));
        // Expected output: Longest length: 6

        List<String> words2 = List.of();
        int maxLen2 = longestStringLengthOrZero(words2);
        log.info("Longest length (or zero): {}", maxLen2);
        // Expected output: Longest length (or zero): 0
    }
}
