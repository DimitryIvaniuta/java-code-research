package com.code.research.datastructures.queues.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueApp {

    public static void main(String[] args) {
        // ArrayBlockingQueue with a fixed capacity.
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);
        Thread producerThread = new Thread(new Producer(queue));
        Thread consumerThread = new Thread(new Consumer(queue));

        producerThread.start();
        consumerThread.start();
    }

}
