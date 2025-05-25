package com.code.research.datastructures.algorithm.anagramchecker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnagramCheckerApp {
    public static void main(String[] args) {
        String[][] tests = {
                {"listen", "silent"},
                {"triangle", "integral"},
                {"Apple", "papel"},       // case-sensitive: false
                {"Apple".toLowerCase(),    // normalization to lowercase
                        "papel".toLowerCase()},
                {"hello", "billion"}
        };

        for(String[] pair: tests) {
            String word1 = pair[0];
            String word2 = pair[1];
            log.info("{} and {} is anagram: {}", word1, word2, AnagramChecker.isAnagram(word1, word2));
            log.info("{} and {} is anagram unicode: {}", word1, word2, AnagramChecker.isAnagramUnicodeSafe(word1, word2));
            log.info("{} and {} is anagram stream: {}", word1, word2, AnagramChecker.isAnagramByStream(word1, word2));
            log.info("{} and {} is anagram map: {}", word1, word2, AnagramChecker.isAnagramByMap(word1, word2));
            log.info("-------------------------------------------------");
        }
    }
}
