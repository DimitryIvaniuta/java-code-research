package com.code.research.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class ExecutorServiceApp {

    public static void main(String[] args) {
        // 5 OS threads
        try (ExecutorService pool = Executors.newFixedThreadPool(5)) {
            Future<Integer> future = pool.submit(() -> {
                // runs on one of those 5 threads
                Thread.sleep(1000);
                return 42;
            });
            Integer result = future.get(); // blocks one OS thread until done
            pool.shutdown();
            log.info("Thread result: {}", result);
        } catch (InterruptedException e) {
            // Re-interrupt the current thread and handle the interruption appropriately.
            Thread.currentThread().interrupt();
            log.info("Thread was interrupted: " + e.getMessage());
        } catch (ExecutionException e) {
            log.error("Thread Execution Exception", e);
        }
    }

}
