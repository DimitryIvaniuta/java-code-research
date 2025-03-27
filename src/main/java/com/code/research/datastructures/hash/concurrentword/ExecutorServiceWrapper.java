package com.code.research.datastructures.hash.concurrentword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorServiceWrapper is a helper class that wraps an ExecutorService
 * and implements AutoCloseable, so that it can be used in a try-with-resources block.
 */
@Data
@AllArgsConstructor
@Slf4j
public class ExecutorServiceWrapper implements AutoCloseable {

    private final ExecutorService executorService;

    /**
     * Shuts down the ExecutorService gracefully.
     */
    @Override
    public void close() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    log.error("Executor did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
