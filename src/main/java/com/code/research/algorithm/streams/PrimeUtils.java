package com.code.research.algorithm.streams;

import java.util.List;
import java.util.stream.IntStream;

public class PrimeUtils {

    /**
     * Returns a list of all prime numbers found in the input array,
     * sorted in ascending order.
     *
     * @param numbers the input array of ints
     * @return List of prime numbers, sorted
     */
    public static List<Integer> getSortedPrimes(int[] numbers) {
        return IntStream.of(numbers)
                .filter(PrimeUtils::isPrime)
                .sorted()
                .boxed()
                .toList();
    }

    /**
     * Checks whether a given integer is prime.
     *
     * @param n the number to test
     * @return true if n is prime, false otherwise
     */
    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        int limit = (int) Math.sqrt(n);
        for (int i = 5; i <= limit; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    // Example usage
    public static void main(String[] args) {
        int[] data = {10, 2, 3, 4, 17, 19, 20, 1, 23, 29, 18, 97, -5, 0};
        List<Integer> primes = getSortedPrimes(data);
        System.out.println(primes);
        // Expected output: [2, 3, 17, 19, 23, 29, 97]
    }

}
