package com.code.research.datastructures.queues;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class QueueMethodsExample {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // Using poll() - returns immediately.
        String result = queue.poll(); // returns null if queue is empty
        log.info("poll() result: " + result);

        // Using poll() with timeout - waits for up to 2 seconds.
        result = queue.poll(2, TimeUnit.SECONDS);
        log.info("poll(timeout) result: {}", result);

        // Using take() - blocks indefinitely until an element is available.
        // (In this example, start a producer thread to add an element after 1 second.)
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                queue.put("Element added later");
                log.info("Element added to queue.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // This call will block until the element is available.
        result = queue.take();
        log.info("take() result: {}", result);
    }
    
}
