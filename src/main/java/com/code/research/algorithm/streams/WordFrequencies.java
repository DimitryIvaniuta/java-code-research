package com.code.research.algorithm.streams;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordFrequencies {


    /**
     * Counts the occurrences of each word in the given list.
     *
     * @param words the input List of String words
     * @return a Map from each distinct word to its frequency count
     */
    public static Map<String, Long> countWordFrequencies(List<String> words) {
        return words.stream().collect(
                Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                )
        );
    }

    public static void main(String[] args) {
        List<String> text = List.of(
                "apple", "banana", "apple", "cherry",
                "banana", "apple", "", "   ", "date"
        );

        Map<String, Long> freqs = countWordFrequencies(text);
        freqs.forEach((word, count) ->
                System.out.printf("%s → %d%n", word, count)
        );
        // prints:
        // apple → 3
        // banana → 2
        // cherry → 1
        // date → 1
    }
}
