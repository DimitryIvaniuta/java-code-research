package com.code.research.concurrent.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoCloseableExecutorService implements AutoCloseable {

    private final ExecutorService executor;

    public AutoCloseableExecutorService(final ExecutorService executor) {
        this.executor = executor;
    }

    public ExecutorService get() {
        return executor;
    }

    @Override
    public void close() {
        // Graceful shutdown when done
        executor.shutdown();
        try{
            // Wait up to 60 seconds for tasks to complete
            if(!executor.awaitTermination(60, TimeUnit.SECONDS)){
                // Cancel currently executing tasks
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // (Re-)Cancel if current thread also interrupted
            executor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
