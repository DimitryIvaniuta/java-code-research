package com.code.research.datastructures.queues.arrayblockingqueue;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Data
@Slf4j
public class Producer implements Runnable {

    private final BlockingQueue<Message> queue;

    @Override
    public void run() {
        try{
            for (int i = 1; i <= 5; i++) {
                Message msg = new Message(i, "Message " + i);
                log.info("Producing: {}", msg);
                queue.put(msg); // Blocks if the queue is full.
                Thread.sleep(500);
            }
            // Indicate the end of production.
            queue.put(new Message(-1, "EOF"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

}
