package com.code.research.threads;

import lombok.extern.slf4j.Slf4j;

/**
 * SharedCounter demonstrates using synchronization to manage access to a shared resource.
 */
@Slf4j
public class SharedCounterApplication {

    /**
     * Demonstrates the usage of the SharedCounter in a multi-threaded scenario.
     */
    public static void main(String[] args) {
        SharedCounter sharedCounter = new SharedCounter();

        // Create 5 threads that increment the counter 1000 times each.
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    sharedCounter.increment();
                }
            });
            threads[i].start();
        }

        // Wait for all threads to complete.
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Print the final counter value.
        log.info("Final Counter Value: {}", sharedCounter.getCounter());
    }
}
