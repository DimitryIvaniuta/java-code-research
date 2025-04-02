package com.code.research.datastructures.queues.concurrentlinkedqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class ConcurrentLinkedQueueApplication {

    public static void main(String[] args) {

        // ConcurrentLinkedQueue is a non-blocking, thread-safe queue.
        Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();

        // Simulate concurrent producers.
        Thread producer1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                eventQueue.add(new Event(i, "Producer1 event " + i));
            }
        });

        Thread producer2 = new Thread(() -> {
            for (int i = 6; i <= 10; i++) {
                eventQueue.add(new Event(i, "Producer2 event " + i));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer1.start();
        producer2.start();

        try {
            producer1.join();
            producer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Process all events (processing can be concurrent if needed).
        eventQueue.forEach(event -> log.info("Processing: {}", event));

    }

}
