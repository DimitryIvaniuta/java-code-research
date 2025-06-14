package com.code.research.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ForkJoinSumTaskApp {

    /**
     * Example usage: fills an array with random values, sums it with Fork/Join,
     * and compares against a single-threaded sum.
     */
    public static void main(String[] args) {
        final int SIZE = 10_000_000;
        int[] data = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            data[i] = ThreadLocalRandom.current().nextInt(100);
        }

        try (ForkJoinPool pool = new ForkJoinPool()) {
            // Warm up
            pool.invoke(new ForkJoinSumTask(data));

            // Measure Fork/Join performance
            long start = System.nanoTime();
            long forkJoinSum = pool.invoke(new ForkJoinSumTask(data));
            long durationFJ = System.nanoTime() - start;
            log.info("Fork/Join sum: {} (took {} ms)",
                    forkJoinSum, durationFJ / 1_000_000.0);

            // Measure single-threaded performance
            start = System.nanoTime();
            long simpleSum = 0;
            for (int v : data) {
                simpleSum += v;
            }
            long durationSingle = System.nanoTime() - start;
            log.info("Sequential sum: {} (took {} ms)",
                    simpleSum, durationSingle / 1_000_000.0);

            pool.shutdown();
        }
    }

}
