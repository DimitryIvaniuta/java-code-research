package com.code.research.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ConcurrentHashMapExample {

    public static void main(String[] args) {

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // Atomic putIfAbsent
        map.putIfAbsent("apple", 1);

        // Compute a new value if key is present
        map.compute("apple", (key, value) -> value == null ? 1 : value + 1);

        // Atomic retrieval and update: merge operation
        map.merge("banana", 1, Integer::sum);

        // Iteration using forEach, which is safe and weakly consistent
        map.forEach((key, value) -> log.info("ConcurrentHashMap key/value {}:{}", key, value));
    }

}
