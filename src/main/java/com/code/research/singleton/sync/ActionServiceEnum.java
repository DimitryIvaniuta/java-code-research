package com.code.research.singleton.sync;

import java.util.concurrent.locks.ReentrantLock;

public enum ActionServiceEnum {
    INSTANCE;

    private final Object lock = new Object();

    // Example shared state guarded by 'lock'
    private int counter;

    private String lastResult;

    public void doAction1() {
        // do non-shared work first (no lock needed)
        var local = computeSomething();

        synchronized (lock) {             // critical section: touches shared state
            counter++;
            lastResult = "A:" + local;
        }                                 // lock released → visibility guaranteed
    }

    public String doAction2() {
        synchronized (lock) {             // uses the same lock → mutually exclusive
            counter += 10;
            return lastResult;              // read is consistent with updates
        }
    }

    private String computeSomething() {
        return "B:" + counter;
    }
}
