package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class GroupingExample {

    public static void main(String[] args) {
        List<String> animals = List.of("cat", "dog", "cow", "donkey", "camel", "dolphin");

        // Group animals by the first letter
        Map<Character, List<String>> grouped = animals.stream()
                .collect(Collectors.groupingBy(animal -> animal.charAt(0)));
        log.info("Grouped by first letter: " + grouped);

        // Partition animals into those starting with 'd' (case-insensitive) and others
        Map<Boolean, List<String>> partitioned = animals.stream()
                .collect(Collectors.partitioningBy(animal -> animal.toLowerCase().startsWith("d")));
        log.info("Partitioned by starting with 'd': " + partitioned);
    }

}

