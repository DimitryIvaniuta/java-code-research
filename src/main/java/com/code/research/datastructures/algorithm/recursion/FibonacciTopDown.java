package com.code.research.datastructures.algorithm.recursion;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FibonacciTopDown {

    // Cache to store computed Fibonacci numbers.
    private final Map<Integer, Long> memo = new HashMap<>();

    /**
     * Computes the nth Fibonacci number using top-down dynamic programming.
     *
     * @param n the index of the Fibonacci number to compute.
     * @return the nth Fibonacci number.
     */
    public long fib(int n) {
        if (n <= 1) {
            return n;
        }
        // Return cached value if available.
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        long result = fib(n - 1) + fib(n - 2);
        memo.put(n, result);
        return result;
    }

    public static void main(String[] args) {
        FibonacciTopDown fibonacci = new FibonacciTopDown();
        log.info("Fibonacci Top-Down (10) = {}", fibonacci.fib(10));
    }
}
