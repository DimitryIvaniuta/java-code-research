package com.code.research.singleton.sync;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe singleton with fine-grained synchronization using:
 * - fair ReentrantLock (no monitor pinning, supports tryLock/interruptible)
 * - Condition for "wait until updated"
 * - fair Semaphore to bound concurrent writers (backpressure)
 */
public enum ActionServiceLockConditionSemaphore {
    INSTANCE;

    // === Concurrency primitives ===
    private final ReentrantLock lock = new ReentrantLock(true);          // fairness=true
    private final Condition updated = lock.newCondition();                // signaled after each write
    private final Semaphore writerSlots = new Semaphore(16, true);        // limit 16 concurrent writers (fair)

    // === Shared mutable state (GUARDED_BY: lock) ===
    private long version = 0L;
    private long counter = 0L;
    private String lastResult = null;

    // Public DTO for a consistent read snapshot
    public record Snapshot(long version, long counter, String lastResult) {}

    /**
     * Writer: performs non-shared work outside the lock,
     * then updates shared state under lock and signals waiters.
     * Uses interruptible acquisition and bounded writer concurrency.
     */
    public void doAction1() throws InterruptedException {
        // Backpressure: only N writers may proceed to the critical section at once
        writerSlots.acquire(); // interruptible; use acquireUninterruptibly() if you prefer "must proceed"
        try {
            // ---- non-shared, potentially heavy work (no contention here) ----
            String local = computeWithoutSharing();

            // ---- shared state mutation (minimal critical section) ----
            lock.lockInterruptibly();          // responsive to cancel/shutdown
            try {
                counter++;
                lastResult = "A:" + local;
                version++;
                updated.signalAll();           // wake up threads waiting for a newer version
            } finally {
                lock.unlock();
            }

            // ---- optional I/O AFTER releasing the lock to avoid lock hold during blocking ----
            maybeDoIO();
        } finally {
            writerSlots.release();
        }
    }

    /**
     * Reader: tries a non-blocking read via tryLock fast-path; on contention,
     * falls back to interruptible acquisition. Returns a consistent snapshot.
     */
    public Snapshot doAction2() throws InterruptedException {
        // Fast-path: avoid queuing if we can read immediately
        if (lock.tryLock()) {
            try {
                return new Snapshot(version, counter, lastResult);
            } finally {
                lock.unlock();
            }
        }
        // Contended path: be interruptible (e.g., shutdown/cancel)
        lock.lockInterruptibly();
        try {
            return new Snapshot(version, counter, lastResult);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Block until state advances beyond 'sinceVersion' or timeout elapses.
     * Demonstrates a Condition with proper handling of spurious wakeups.
     */
    public Snapshot waitUntilUpdated(long sinceVersion, long timeout, TimeUnit unit)
            throws InterruptedException, TimeoutException {
        long nanos = unit.toNanos(timeout);
        lock.lockInterruptibly();
        try {
            while (version <= sinceVersion) {
                if (nanos <= 0L) {
                    throw new TimeoutException("Timed out waiting for update > " + sinceVersion);
                }
                nanos = updated.awaitNanos(nanos); // spurious wakeups handled by the loop predicate
            }
            return new Snapshot(version, counter, lastResult);
        } finally {
            lock.unlock();
        }
    }

    // ------------------ helpers (outside the critical section) ------------------

    private String computeWithoutSharing() {
        // heavy CPU/JSON parsing/etc. — intentionally outside the lock
        return Long.toHexString(System.nanoTime());
    }

    private void maybeDoIO() {
        // e.g., metrics/logging — intentionally outside the lock to avoid lock hold during blocking
    }

}
