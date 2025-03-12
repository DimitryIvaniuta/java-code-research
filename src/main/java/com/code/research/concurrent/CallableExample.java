package com.code.research.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class CallableExample {

    public static void main(String[] args) {
        // Using lambda to implement Callable which returns an Integer result.
        try (ExecutorServiceResource executorResource =
                     new ExecutorServiceResource(Executors.newSingleThreadExecutor())) {

            Callable<Integer> task = () -> {
                log.info("Computing in thread: " + Thread.currentThread().getName());
                return 42;
            };

            Future<Integer> future = executorResource.getExecutorService().submit(task);

            // Retrieve the result of the computation.
            Integer result = future.get();
            log.info("Result: " + result);

        } catch (InterruptedException e) {
            // Re-interrupt the current thread and handle the interruption appropriately.
            Thread.currentThread().interrupt();
            log.info("Thread was interrupted: " + e.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
