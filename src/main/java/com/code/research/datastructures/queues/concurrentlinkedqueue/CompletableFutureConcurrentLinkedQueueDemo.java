package com.code.research.datastructures.queues.concurrentlinkedqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFutureConcurrentLinkedQueueDemo {

    public static void main(String[] args) {
        // Create a thread-safe, non-blocking queue.
        Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();

        try(ExecutorServiceWrapper executorServiceWrapper = new ExecutorServiceWrapper(Executors.newFixedThreadPool(2))){
            // Producer1: adds events immediately.
            CompletableFuture<Void> producer1 = CompletableFuture.runAsync(() -> {
                for (int i = 1; i <= 5; i++) {
                    Event event = new Event(i, "Producer1 event " + i);
                    eventQueue.add(event);
                    log.info("Producer1 added " + event);
                }
            }, executorServiceWrapper.getExecutor());

            // Producer2: adds events after a 500ms delay.
            Executor delayedExecutor = CompletableFuture.delayedExecutor(500, TimeUnit.MILLISECONDS, executorServiceWrapper.getExecutor());
            CompletableFuture<Void> producer2 = CompletableFuture.runAsync(() -> {
                for (int i = 6; i <= 10; i++) {
                    Event event = new Event(i, "Producer2 event " + i);
                    eventQueue.add(event);
                    log.info("Producer2 added " + event);
                }
            }, delayedExecutor);

            // Wait for both producer tasks to complete.
            CompletableFuture.allOf(producer1, producer2).join();

            // Process all events.
            eventQueue.forEach(event -> log.info("Processing {}", event));
        }

    }

}
