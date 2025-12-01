package com.code.research.algorithm;

import java.util.HashMap;
import java.util.Map;

public class WordFrequencyApp {

    public static void main(String[] args) {
        String text = "Hello world, hello Java world!";

        // Normalize: lower-case and split on non-word characters
        String[] words = text.toLowerCase().split("\\W+");

        Map<String, Integer> freq = new HashMap<>();

        for (String word : words) {
            if (word.isEmpty()) {
                continue; // skip empty tokens if any
            }
            // increment count for this word
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }

        // Print result
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
