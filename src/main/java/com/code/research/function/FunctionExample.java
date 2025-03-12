package com.code.research.function;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

@Slf4j
public class FunctionExample {

    public static void main(String[] args) {
        // Use ToIntFunction to convert a String to its length, avoiding boxing.
        ToIntFunction<String> lengthFunction = String::length;
        String testString = "Hello, Java!";
        log.info("Length of '{}' is: {}", testString, lengthFunction.applyAsInt(testString));

        // Using UnaryOperator to convert a string to uppercase.
        UnaryOperator<String> toUpperCase = String::toUpperCase;
        log.info("Uppercase: {}", toUpperCase.apply(testString));
    }

}
