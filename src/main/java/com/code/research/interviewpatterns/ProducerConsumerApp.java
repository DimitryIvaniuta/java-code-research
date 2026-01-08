package com.code.research.interviewpatterns;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Producer–Consumer using BlockingQueue: producers put(), consumers take().
 */
public final class ProducerConsumerApp {
    // Special value to tell consumers to stop
    private static final int POISON = Integer.MIN_VALUE;

    public static void main(String[] args) throws InterruptedException {
        int producers = 2, consumers = 3, capacity = 5, itemsPerProducer = 10;
        BlockingQueue<Integer> q = new ArrayBlockingQueue<>(capacity);   // queue does the waiting

        ExecutorService pool = Executors.newCachedThreadPool();
        AtomicInteger seq = new AtomicInteger(1);                        // simple ID generator

        // Producers: generate numbers and put() (blocks if full)
        for (int p = 0; p < producers; p++) {
            int id = p;
            pool.submit(() -> {
                try {
                    for (int i = 0; i < itemsPerProducer; i++) {
                        int item = seq.getAndIncrement();
                        q.put(item);                                     // waits if full
                        System.out.printf("P%d -> %d (q=%d)%n", id, item, q.size());
                        // TimeUnit.MILLISECONDS.sleep(50);              // optional pacing
                    }
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Consumers: take() (blocks if empty) until poison pill
        for (int c = 0; c < consumers; c++) {
            int id = c;
            pool.submit(() -> {
                try {
                    while (true) {
                        int item = q.take();                              // waits if empty
                        if (item == POISON) {
                            System.out.printf("C%d <- POISON%n", id);
                            break;
                        }
                        System.out.printf("C%d <- %d (q=%d)%n", id, item, q.size());
                        // process(item);
                    }
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Wait for producers to finish, then send one poison per consumer
        pool.shutdown();                                                 // no new tasks
        // simpler: spin until queue drains of production; here we just wait a bit for demo:
        pool.awaitTermination(1, TimeUnit.SECONDS);                      // let producers enqueue
        for (int i = 0; i < consumers; i++) q.put(POISON);               // stop signal

        // Give consumers time to exit
        pool.awaitTermination(2, TimeUnit.SECONDS);
    }
}
