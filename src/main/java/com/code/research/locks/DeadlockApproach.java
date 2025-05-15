package com.code.research.locks;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class DeadlockApproach {

    // Two locks simulating shared resources
    private static final ReentrantLock LOCK_A = new ReentrantLock();
    private static final ReentrantLock LOCK_B = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Demonstrating deadlock ===");
        Thread t1 = new Thread(() -> deadlockTask(LOCK_A, LOCK_B), "Thread-1");
        Thread t2 = new Thread(() -> deadlockTask(LOCK_B, LOCK_A), "Thread-2");
        t1.start();
        t2.start();

        // Start a detector that wakes up after 3s and checks for deadlocks
        Thread detector = new Thread(DeadlockApproach::detectDeadlock, "Deadlock-Detector");
        detector.setDaemon(true);
        detector.start();

        // Let the deadlock sit for a bit
        Thread.sleep(5_000);

        System.out.println("\n=== Demonstrating prevention via lock ordering ===");
        Thread p1 = new Thread(() -> orderedTask("P1", LOCK_A, LOCK_B), "Prevent-1");
        Thread p2 = new Thread(() -> orderedTask("P2", LOCK_B, LOCK_A), "Prevent-2");
        p1.start();
        p2.start();
        p1.join();
        p2.join();

        System.out.println("Both prevention threads completed without deadlock.");
    }

    /**
     * THIS WILL DEADLOCK:
     * Thread tries to lock firstLock, then sleeps, then tries secondLock.
     */
    private static void deadlockTask(ReentrantLock firstLock, ReentrantLock secondLock) {
        try {
            System.out.printf("%s attempting %s%n",
                    Thread.currentThread().getName(), firstLock);
            firstLock.lock();
            System.out.printf("%s acquired %s%n",
                    Thread.currentThread().getName(), firstLock);

            // give the other thread a chance to acquire the other lock
            Thread.sleep(1000);

            System.out.printf("%s attempting %s%n",
                    Thread.currentThread().getName(), secondLock);
            secondLock.lock();
            System.out.printf("%s acquired %s%n",
                    Thread.currentThread().getName(), secondLock);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // In deadlock, this will never be reached
            if (firstLock.isHeldByCurrentThread()) firstLock.unlock();
            if (secondLock.isHeldByCurrentThread()) secondLock.unlock();
        }
    }

    /**
     * DETECTS deadlocks by polling the JVM ThreadMXBean.
     */
    private static void detectDeadlock() {
        try {
            Thread.sleep(3000); // wait for deadlock to appear
        } catch (InterruptedException ignored) {
        }

        ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = tmxb.findDeadlockedThreads();

        if (deadlockedThreads != null) {
            System.err.println("\n*** DEADLOCK DETECTED ***");
            ThreadInfo[] infos = tmxb.getThreadInfo(deadlockedThreads);
            for (ThreadInfo ti : infos) {
                System.err.printf("Thread %s is deadlocked waiting on %s%n",
                        ti.getThreadName(), ti.getLockName());
            }
        } else {
            System.out.println("No deadlock detected.");
        }
    }

    /**
     * PREVENTS deadlock by imposing a global lock order:
     * always acquire the lower‐hashcode lock first.
     */
    private static void orderedTask(String name,
                                    ReentrantLock lock1,
                                    ReentrantLock lock2) {
        ReentrantLock first = lock1, second = lock2;
        // define ordering by identityHashCode
        if (System.identityHashCode(lock1) > System.identityHashCode(lock2)) {
            first = lock2;
            second = lock1;
        }

        try {
            System.out.printf("%s attempting %s%n", name, first);
            first.lock();
            System.out.printf("%s acquired %s%n", name, first);

            // simulate work
            Thread.sleep(500);

            System.out.printf("%s attempting %s%n", name, second);
            second.lock();
            System.out.printf("%s acquired %s%n", name, second);

            // actual business logic...
            System.out.printf("%s performing work%n", name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (second.isHeldByCurrentThread()) second.unlock();
            if (first.isHeldByCurrentThread()) first.unlock();
        }
    }
}
