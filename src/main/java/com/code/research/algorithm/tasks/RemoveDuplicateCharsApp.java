package com.code.research.algorithm.tasks;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
public class RemoveDuplicateCharsApp {

    /**
     * Returns a new string with duplicate characters removed,
     * preserving the order of first occurrences.
     *
     * @param input the original string
     * @return a string with duplicates removed
     */
    public static String removeDuplicateChars(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        Set<Character> seen = new LinkedHashSet<>(input.length());
        StringBuilder result = new StringBuilder(input.length());

        for (char c : input.toCharArray()) {
            if (seen.add(c)) {        // add() returns false if c was already present
                result.append(c);
            }
        }

        return result.toString();
    }

    // Simple demonstration
    public static void main(String[] args) {
        String[] tests = {
                "banana",
                "hello world",
                "1122334455",
                "",
                null
        };
        for (String test : tests) {
            log.info("Input: {} -> Output: {}",
                    test, removeDuplicateChars(test));
        }
    }

}
