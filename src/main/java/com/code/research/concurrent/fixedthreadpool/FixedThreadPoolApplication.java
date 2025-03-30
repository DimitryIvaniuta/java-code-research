package com.code.research.concurrent.fixedthreadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FixedThreadPoolApplication {

    public static void main(String[] args) {
        // Create a fixed thread pool with 4 threads.
        try (FixedThreadPoolWrapper executorWrapper = new FixedThreadPoolWrapper(
                Executors.newFixedThreadPool(4))) {
            ExecutorService executor = executorWrapper.getExecutor();
            // Submit tasks.
            for (int i = 1; i <= 10; i++) {
                int taskId = i;
                executor.submit(() -> {
                    log.info("Task {} is running on {}", taskId, Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }
    }
}
