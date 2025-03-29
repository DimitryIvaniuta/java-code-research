package com.code.research.threads.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SharedCounterExecutorApplication {

    /**
     * Main method demonstrating the use of a thread pool to concurrently update a shared counter.
     *
     * <p>This example creates a fixed thread pool with 5 threads. It submits 5 tasks,
     * each of which increments the shared counter 1,000 times. The executor is then
     * gracefully shutdown, and the final counter value is printed.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SharedCounterExecutor sharedCounter = new SharedCounterExecutor();

        // Using try-with-resources to ensure that the ExecutorService is properly closed.
        try (ExecutorServiceWrapper executorWrapper = new ExecutorServiceWrapper(
                Executors.newFixedThreadPool(5))) {

            ExecutorService executor = executorWrapper.getExecutorService();

            // Number of tasks (threads) and number of increments per task.
            int numTasks = 5;
            int incrementsPerTask = 1000;

            // Submit tasks to the executor.
            for (int i = 0; i < numTasks; i++) {
                executor.submit(() -> {
                    for (int j = 0; j < incrementsPerTask; j++) {
                        sharedCounter.increment();
                    }
                });
            }
        }

        // Print the final counter value.
        log.info("Final Counter Value: {}", sharedCounter.getCounter());
    }
}
