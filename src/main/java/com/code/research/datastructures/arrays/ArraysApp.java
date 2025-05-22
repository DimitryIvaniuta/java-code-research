package com.code.research.datastructures.arrays;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ArraysApp {

    // Quick test
    public static void main(String[] args) {
        ArraysUtil<String> arraysUtil = new ArraysUtil<>();
        List<Person> people = List.of(
                new Person("Alice", "Zephyr"),
                new Person("Bob",   "Anderson"),
                new Person("Carol", "Anderson")
        );
        arraysUtil.sortByFullName(people)
                .forEach(p -> log.info("{}, {}", p.lastName(),p.firstName()));
    }

    public record Person(String firstName, String lastName) {

    }
}
