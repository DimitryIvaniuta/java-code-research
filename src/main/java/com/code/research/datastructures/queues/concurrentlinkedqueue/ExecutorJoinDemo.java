package com.code.research.datastructures.queues.concurrentlinkedqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ExecutorJoinDemo {

    public static void main(String[] args) {
        // Create a thread-safe, non-blocking queue.
        Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();

        // Create a ScheduledExecutorService with two threads.
        try (ScheduledExecutorServiceWrapper executorServiceWrapper = new ScheduledExecutorServiceWrapper(Executors.newScheduledThreadPool(2))) {
            List<Future<?>> futures = new ArrayList<>();

            Runnable producer1 = () -> {
                for (int i = 1; i <= 5; i++) {
                    Event event = new Event(i, "Producer1 event " + i);
                    eventQueue.add(event);
                    log.info("Producer1 added {}", event);
                }
            };

            // Producer2 task: adds events after a 500ms delay.
            Runnable producer2 = () -> {
                for (int i = 6; i <= 10; i++) {
                    Event event = new Event(i, "Producer2 event " + i);
                    eventQueue.add(event);
                    log.info("Producer2 added {}", event);
                }
            };

            // Submit producer1 immediately.
            futures.add(executorServiceWrapper.getExecutor().submit(producer1));
            // Schedule producer2 to run after 500ms delay.
            futures.add(executorServiceWrapper.getExecutor().schedule(producer2, 500, TimeUnit.MILLISECONDS));

            // Wait for both tasks to complete using Future.get(), which is similar to Thread.join().
            for (Future<?> future : futures) {
                try {
                    future.get(); // This blocks until the task is complete.
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Error waiting for task completion: ", e);
                    Thread.currentThread().interrupt();
                }
            }

        }

        // Process all events after the tasks have completed.
        log.info("\nProcessing events:");
        eventQueue.forEach(event -> log.info("Processing {}", event));
    }

}
