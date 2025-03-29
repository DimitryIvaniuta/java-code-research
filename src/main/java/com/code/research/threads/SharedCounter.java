package com.code.research.threads;

public class SharedCounter {

    // The shared counter variable.
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
