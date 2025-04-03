package com.code.research.datastructures.queues.arrayblockingqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class Consumer implements Runnable {

    private final BlockingQueue<Message> queue;

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message msg = queue.take(); // Blocks if the queue is empty.
                if (msg.getId() == -1) {
                    break;
                }
                log.info("Consuming: {}", msg);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
