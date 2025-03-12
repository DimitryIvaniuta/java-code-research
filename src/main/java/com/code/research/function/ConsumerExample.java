package com.code.research.function;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class ConsumerExample {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // Consumer that prints each name with a prefix.
        Consumer<String> printConsumer = name -> log.info("Name: {}", name);

        // Using forEach method with the Consumer.
        names.forEach(printConsumer);
    }

}
