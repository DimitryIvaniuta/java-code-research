package com.code.research.datastructures.queues.concurrentlinkedqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class ConcurrentLinkedQueueApp {
    
    public static void main(String[] args) {
        // ConcurrentLinkedQueue is a non-blocking, thread-safe queue.
        Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();

        // Producer 1 adds events immediately.
        Thread producer1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                eventQueue.add(new Event(i, "Producer1 event " + i));
                log.info("Producer1 added event " + i);
            }
        });

        // Producer 2 will start after a 500ms delay.
        Thread producer2 = new Thread(() -> {
            for (int i = 6; i <= 10; i++) {
                eventQueue.add(new Event(i, "Producer2 event " + i));
                log.info("Producer2 added event {}", i);
            }
        });

        producer1.start();

        try {
            // Wait 500ms before starting producer2
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        producer2.start();

        try {
            producer1.join();
            producer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Process all events.
        eventQueue.forEach(event -> log.info("Processing {}", event));
    }
}
