package com.code.research.locks;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockApp {

    // Two locks simulating shared resources
    private static final ReentrantLock LOCK_A = new ReentrantLock();
    private static final ReentrantLock LOCK_B = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Demonstrating deadlock ===");
        Thread t1 = new Thread(() -> deadlockTask(LOCK_A, LOCK_B), "Thread-1");
        Thread t2 = new Thread(() -> deadlockTask(LOCK_B, LOCK_A), "Thread-2");
        t1.start();
        t2.start();

        // Detector thread (daemon) that checks for deadlocks after 3s
        Thread detector = new Thread(DeadlockApp::detectDeadlock, "Deadlock-Detector");
        detector.setDaemon(true);
        detector.start();

        // Give the deadlock a chance to form
        Thread.sleep(5_000);

        System.out.println("\n=== Demonstrating prevention via lock ordering ===");
        Thread p1 = new Thread(() -> orderedTask("P1", LOCK_A, LOCK_B), "Prevent-1");
        Thread p2 = new Thread(() -> orderedTask("P2", LOCK_B, LOCK_A), "Prevent-2");
        p1.start();
        p2.start();
        p1.join();
        p2.join();

        System.out.println("Both prevention threads completed without deadlock. Demo finished.");
    }

    private static void deadlockTask(ReentrantLock firstLock, ReentrantLock secondLock) {
        try {
            System.out.printf("%s attempting %s%n",
                    Thread.currentThread().getName(), firstLock);
            firstLock.lock();
            System.out.printf("%s acquired %s%n",
                    Thread.currentThread().getName(), firstLock);

            // let the other thread grab its first lock
            Thread.sleep(1000);

            System.out.printf("%s attempting %s%n",
                    Thread.currentThread().getName(), secondLock);
            secondLock.lock();
            System.out.printf("%s acquired %s%n",
                    Thread.currentThread().getName(), secondLock);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        } finally {
            // In a real deadlock this never runs
            if (firstLock.isHeldByCurrentThread()) firstLock.unlock();
            if (secondLock.isHeldByCurrentThread()) secondLock.unlock();
        }
    }

    private static void detectDeadlock() {
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = tmxb.findDeadlockedThreads();
        if (deadlockedThreads != null) {
            System.err.println("\n*** DEADLOCK DETECTED ***");
            for (ThreadInfo ti : tmxb.getThreadInfo(deadlockedThreads)) {
                System.err.printf("Thread %s is deadlocked on %s%n",
                        ti.getThreadName(), ti.getLockName());
            }
        } else {
            System.out.println("No deadlock detected.");
        }
    }

    private static void orderedTask(String name,
                                    ReentrantLock lock1,
                                    ReentrantLock lock2) {
        // impose a global order on lock acquisition
        ReentrantLock first = lock1, second = lock2;
        if (System.identityHashCode(lock1) > System.identityHashCode(lock2)) {
            first = lock2;
            second = lock1;
        }

        try {
            System.out.printf("%s attempting %s%n", name, first);
            first.lock();
            System.out.printf("%s acquired %s%n", name, first);

            Thread.sleep(500);

            System.out.printf("%s attempting %s%n", name, second);
            second.lock();
            System.out.printf("%s acquired %s%n", name, second);

            System.out.printf("%s performing safe work%n", name);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        } finally {
            if (second.isHeldByCurrentThread()) second.unlock();
            if (first.isHeldByCurrentThread()) first.unlock();
        }
    }
}
