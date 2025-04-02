package com.code.research.datastructures.queues.concurrentlinkedqueue;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceWrapper implements AutoCloseable {

    private final ScheduledExecutorService executor;

    public ScheduledExecutorServiceWrapper(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }

    @Override
    public void close() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
