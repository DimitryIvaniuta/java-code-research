package com.code.research.threads;

import lombok.extern.slf4j.Slf4j;

import static com.code.research.threads.WaitTask.TASK_LOCK;

/**
 * ThreadLifecycle demonstrates the different states a thread can go through in Java.
 * <ul>
 *   <li>NEW: When a Thread object is created but not yet started.</li>
 *   <li>RUNNABLE: When the thread is running or ready to run.</li>
 *   <li>TIMED_WAITING: When the thread is sleeping (e.g., Thread.sleep()).</li>
 *   <li>BLOCKED: When the thread is waiting to acquire a lock.</li>
 *   <li>WAITING: When the thread calls wait() and is waiting for notification.</li>
 *   <li>TERMINATED: When the thread has finished execution.</li>
 * </ul>
 */
@Slf4j
public class ThreadLifecycleApplication {

    public static void main(String[] args) throws InterruptedException {

        // A mutable condition variable to control the waiting thread.
        // We use an array for mutability.
        final boolean[] proceed = {false};

        // Create T1 using a lambda Runnable.
        Thread t1 = new Thread(() -> {
            try {
                // Initially, after start, the thread is RUNNABLE.
                log.info("T1: Started. Current state: {}", Thread.currentThread().getState());

                // Sleep to simulate work and enter TIMED_WAITING state.
                log.info("T1: Sleeping for 1 second (TIMED_WAITING)...");
                Thread.sleep(1000);
                log.info("T1: Woke up from sleep. Current state: {}", Thread.currentThread().getState());

                // Attempt to enter a synchronized block to acquire the lock.
                // If the lock is held by another thread, T1 will become BLOCKED.
                log.info("T1: Attempting to acquire lock...");
                synchronized (TASK_LOCK) {
                    log.info("T1: Acquired lock. Entering wait state (WAITING)...");
                    // Call wait() without a timeout to enter WAITING state.
                    // Use a while loop to wait until the condition is met.
                    while (!proceed[0]) {
                        TASK_LOCK.wait();
                    }
                    log.info("T1: Notified and resumed execution.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("T1: Interrupted!");
            }
            log.info("T1: Completed execution (TERMINATED).");
        });

        // Before starting, T1 is in NEW state.
        log.info("T1 initial state: {}", t1.getState()); // Expected: NEW

        // Start T1.
        t1.start();
        Thread.sleep(100); // Give T1 time to start.
        log.info("T1 state after start (should be RUNNABLE): " + t1.getState());

        // Create T2 to simulate a thread holding the lock.

        Thread t2 = new Thread(new WaitTask(2000));
        t2.start();

        // Wait until T1 wakes from sleep and attempts to acquire the lock.
        Thread.sleep(1100);
        log.info("T1 state while trying to acquire lock (should be BLOCKED): " + t1.getState());

        // After T2 releases the lock, T1 will eventually acquire it and then call wait(), entering WAITING state.
        // Notify T1 to resume after wait.
        Thread.sleep(1500);
        synchronized (TASK_LOCK) {
            log.info("Main thread: Notifying all waiting threads on lock.");
            proceed[0] = true;
            TASK_LOCK.notifyAll();
        }

        // Wait for both threads to complete.
        t1.join();
        t2.join();
        log.info("T1 final state: " + t1.getState()); // Expected: TERMINATED
    }

}
