package com.code.research.concurrent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunnableExample {

    public static void main(String[] args) {
        // Using lambda to implement Runnable.
        Runnable task = () -> log.info("Task executed in thread: {}", Thread.currentThread().getName());

        // Creating and starting a new thread with the Runnable.
        Thread thread = new Thread(task);
        thread.start();
    }

}
