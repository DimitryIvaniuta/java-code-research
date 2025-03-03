package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class SortingExample {

    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>(List.of("Orange", "strawberry", "Apple", "banana", "Kiwi", "Cherry"));

        // Sort using natural ordering
        Collections.sort(fruits);
        log.info("Sorted notural Fruits: {}", fruits);

        fruits.sort(Comparator.comparing(String::toLowerCase));
        log.info("Sorted Fruits by name: {}", fruits);

        // Sort using a custom comparator (by length, then alphabetically)
        fruits.sort(Comparator.comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder()));
        log.info("Sorted by length then alphabetically: {}", fruits);
    }

}
