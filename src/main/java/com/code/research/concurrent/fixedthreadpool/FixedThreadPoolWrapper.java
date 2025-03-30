package com.code.research.concurrent.fixedthreadpool;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FixedThreadPoolWrapper implements AutoCloseable {

    @Getter
    private final ExecutorService executor;

    public FixedThreadPoolWrapper(final ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void close() {
        // Shutdown the pool.
        this.executor.shutdown();
        try {
            if (!this.executor.awaitTermination(60, TimeUnit.SECONDS)) {
                this.executor.shutdownNow();
                if (!this.executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    log.error("Executor did not terminate");
                }
            }
        } catch (InterruptedException e) {
            this.executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
