package com.code.research.function;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
public class FunctionalInterfaceExamples {

    public static void main(String[] args) {
        // 1. BinaryOperator<Integer>: Sum two integers.
        IntBinaryOperator add = Integer::sum;
        int sum = add.applyAsInt(10, 20);
        log.info("Sum of 10 and 20: " + sum);

        // 2. BiFunction<String, Integer, String>: Repeat a string 'n' times.
        BiFunction<String, Integer, String> repeatString = (s, n) -> s.repeat(Math.max(0, n));
        String repeated = repeatString.apply("Hi ", 3);
        log.info("Repeated String: {}", repeated);

        // 3. UnaryOperator<String>: Trim a string and convert it to uppercase.
        UnaryOperator<String> transform = str -> str.trim().toUpperCase() + "!";
        String transformed = transform.apply("   hello world   ");
        log.info("Transformed String: {}", transformed);

        // 4. BinaryOperator<String>: Concatenate two strings with a space.
        BinaryOperator<String> concatenate = (s1, s2) -> s1 + " " + s2;
        String concatenated = concatenate.apply("Hello", "World");
        log.info("Concatenated String: {}", concatenated);

        List<String> names = List.of("string1 ", "string2 ", "string3 ");
        String result = names.stream().map(transform).collect(Collectors.joining(", "));
        log.info("Result: {}", result);
    }

}
