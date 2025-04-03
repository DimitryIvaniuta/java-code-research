package com.code.research.datastructures.queues.linkedblockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueApp {

    public static void main(String[] args) {
        // LinkedBlockingQueue which can be unbounded (or bounded if specified).
        BlockingQueue<LogEntry> queue = new LinkedBlockingQueue<>();
        Thread producer = new Thread(new LoggerProducer(queue));
        Thread consumer = new Thread(new LoggerConsumer(queue));

        producer.start();
        consumer.start();
    }

}
