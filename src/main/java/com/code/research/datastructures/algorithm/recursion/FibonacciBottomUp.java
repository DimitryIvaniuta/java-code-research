package com.code.research.datastructures.algorithm.recursion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FibonacciBottomUp {

    public long fib(int n) {
        if (n <= 1) {
            return n;
        }
        long[] dp = new long[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        FibonacciTopDown fibonacci = new FibonacciTopDown();
        log.info("Fibonacci Bottom-Up (10) = {}", fibonacci.fib(10));
    }

}
