package com.code.research.concurrent;

import java.util.concurrent.ExecutorService;

public class ExecutorServiceResource implements AutoCloseable {

    private final ExecutorService executorService;

    public ExecutorServiceResource(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    @Override
    public void close() {
        // Initiates an orderly shutdown
        executorService.shutdown();
    }
}
