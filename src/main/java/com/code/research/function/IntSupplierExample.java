package com.code.research.function;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.function.IntSupplier;

@Slf4j
public class IntSupplierExample {

    // Reusable Random instance
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // Create an IntSupplier that generates a random integer between 0 (inclusive) and 100 (exclusive)
        IntSupplier randomIntSupplier = createRandomIntSupplier(100);

        // Retrieve the value using getAsInt()
        int randomValue = randomIntSupplier.getAsInt();
        log.info("Random int: {}", randomValue);
    }

    /**
     * Returns an IntSupplier that produces random integers between 0 (inclusive) and the specified bound (exclusive).
     *
     * @param bound the upper bound (exclusive) for random values
     * @return an IntSupplier providing random integers
     */
    public static IntSupplier createRandomIntSupplier(final int bound) {
        return () -> RANDOM.nextInt(bound);
    }

}
