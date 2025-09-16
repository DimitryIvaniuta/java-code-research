package com.code.research.singleton.sync;

import java.util.concurrent.locks.ReentrantLock;

public enum ActionServiceLock {
    INSTANCE;

    private final ReentrantLock lock = new ReentrantLock(true);
    private int counter;
    private String last;


    public void doAction1() {
        var local = compute();
        lock.lock();
        try {
            counter++;
            last = "A:" + local;
        } finally {
            lock.unlock();
        }

    }

    public String doAction2() {
        if (lock.tryLock()) {        // optional fast path
            try {
                return last;
            } finally {
                lock.unlock();
            }
        }
        lock.lock();
        try {
            return last;
        } finally {
            lock.unlock();
        }
    }

    private String compute() {
        return String.valueOf(counter);
    }

}

