package com.code.research.algorithm.test.streams;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JoinDistinctSorted {

    public static String joinDistinctSorted(List<String> words){
        return words.stream()
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
    }

    public static void main(String[] args) {
        List<String> words = List.of("banana", "apple", "orange", "banana", "apple");
        String result = joinDistinctSorted(words);
        log.info("Join Result: {}", result);
        // Expected output: apple, banana, orange
    }

}
