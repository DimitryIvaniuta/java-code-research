package com.code.research.livecoding.streams;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordUtils {

    /**
     * Counts how many times each word appears in the given list.
     *
     * @param words the input List of words
     * @return a Map where keys are words and values are their occurrence counts
     */
    public static Map<String, Long> wordFrequencies(List<String> words) {
        return words.stream()
                .collect(
                        Collectors.groupingBy(Function.identity(),
                                Collectors.counting()
                        )
                );
    }

    public static void main(String[] args) {
        List<String> corpus = List.of(
                "apple", "banana", "apple", "cherry",
                "banana", "apple", "date", "banana"
        );

        Map<String, Long> freqs = wordFrequencies(corpus);
        System.out.println("Fords Frequency: " + freqs);
    }

}
