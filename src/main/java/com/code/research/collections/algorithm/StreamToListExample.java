package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StreamToListExample {

    public static void main(String[] args) {
        List<String> names = List.of("Anna", "Brian", "Clara", "David", "Chris");

        // Filter names starting with 'C' and collect to an unmodifiable list
        List<String> cNames = names.stream()
                .filter(name -> name.startsWith("C"))
                .toList();
        log.info("Names starting with C: {}", cNames);
    }

}
