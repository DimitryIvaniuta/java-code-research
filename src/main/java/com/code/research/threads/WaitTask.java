package com.code.research.threads;

import lombok.extern.slf4j.Slf4j;

/**
 * WaitTask is a Runnable that waits for a specified duration while releasing the lock.
 * The wait is implemented in a while loop to account for spurious wake-ups.
 */
@Slf4j
public class WaitTask implements Runnable {

    /**
     * A shared lock object used to synchronize access.
     */
    public static final Object TASK_LOCK = new Object();

    /**
     * The duration (in milliseconds) for which the thread should wait.
     */
    private final long waitDurationMillis;

    /**
     * Constructs a new WaitTask with the specified wait duration.
     *
     * @param waitDurationMillis the time in milliseconds the thread should wait.
     */
    public WaitTask(long waitDurationMillis) {
        this.waitDurationMillis = waitDurationMillis;
    }

    @Override
    public void run() {
        synchronized (TASK_LOCK) {
            long waitEndTime = System.currentTimeMillis() + waitDurationMillis;
            log.info("T2: Acquired lock, will wait for {} ms (until {})", waitDurationMillis, waitEndTime);
            try {
                // Wait in a loop until the full wait duration has elapsed.
                while (true) {
                    long remainingMillis = waitEndTime - System.currentTimeMillis();
                    if (remainingMillis <= 0) {
                        break;
                    }
                    TASK_LOCK.wait(remainingMillis);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("T2: Interrupted while waiting", e);
            }
            log.info("T2: Finished waiting and now releasing lock.");
        }
    }
}