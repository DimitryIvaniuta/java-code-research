package com.code.research.datastructures.arrays;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.IntStream;

@Slf4j
public class FibonacciDP {

    public static void main(String[] args) {
        int n = 10; // Compute first 10 Fibonacci numbers
        int[] fib = new int[n];
        fib[0] = 0;
        fib[1] = 1;
        IntStream.range(2, n)
                .forEach(i -> fib[i] = fib[i - 1] + fib[i - 2]);
        log.info("First 10 Fibonacci numbers:");
        Arrays.stream(fib)
                .forEach(num -> log.info("{}; ", num));

    }

}
