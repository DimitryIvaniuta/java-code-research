package com.code.research.threads.executor;

/**
 * SharedCounter demonstrates how to use synchronization to manage a shared resource,
 * and how to execute tasks concurrently using a thread pool with ExecutorService.
 */
public class SharedCounterExecutor {

    /**
     * The shared counter variable. Access to this variable is protected by synchronized methods.
     */
    private int counter = 0;

    /**
     * Increments the counter by one in a thread-safe manner.
     */
    public synchronized void increment() {
        counter++;
    }

    /**
     * Returns the current value of the counter in a thread-safe manner.
     *
     * @return the current counter value
     */
    public synchronized int getCounter() {
        return counter;
    }

}
