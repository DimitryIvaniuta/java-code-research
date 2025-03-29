package com.code.research.threads.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorServiceWrapper is a helper class that wraps an ExecutorService
 * and implements AutoCloseable so that it can be used in a try-with-resources block.
 */
@Slf4j
class ExecutorServiceWrapper implements AutoCloseable {

    /**
     * The wrapped ExecutorService.
     */
    private final ExecutorService executorService;

    /**
     * Constructs a new ExecutorServiceWrapper with the provided ExecutorService.
     *
     * @param executorService the ExecutorService to wrap.
     */
    public ExecutorServiceWrapper(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * Returns the wrapped ExecutorService.
     *
     * @return the ExecutorService instance.
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * Closes the ExecutorService by initiating a shutdown and awaiting termination.
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
