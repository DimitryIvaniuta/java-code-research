package com.code.research.datastructures.queues.concurrentlinkedqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConcurrentLinkedQueueThreadPoolDemo {

    public static void main(String[] args) {
        // Create a thread-safe, non-blocking queue.
        Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();

        // Use try-with-resources to manage the ScheduledExecutorService.
        try (ScheduledExecutorServiceWrapper executorWrapper =
                     new ScheduledExecutorServiceWrapper(Executors.newScheduledThreadPool(2))) {

            ScheduledExecutorService executor = executorWrapper.getExecutor();

            // Producer1 task: adds events immediately.
            Runnable producer1 = () -> {
                for (int i = 1; i <= 5; i++) {
                    Event event = new Event(i, "Producer1 event " + i);
                    eventQueue.add(event);
                    log.info("Producer1 added " + event);
                }
            };

            // Producer2 task: adds events after a 500ms delay.
            Runnable producer2 = () -> {
                for (int i = 6; i <= 10; i++) {
                    Event event = new Event(i, "Producer2 event " + i);
                    eventQueue.add(event);
                    log.info("Producer2 added " + event);
                }
            };

            // Schedule producer2 to run after 10ms.
            executor.schedule(producer1, 10, TimeUnit.MILLISECONDS);
            // Submit producer1 immediately.
            executor.submit(producer2);
        } // The executor is automatically shut down here.

        // Process all events after the executor has been closed.
        log.info("\nProcessing events:");
        eventQueue.forEach(event -> log.info("Processing " + event));
    }
    
}
