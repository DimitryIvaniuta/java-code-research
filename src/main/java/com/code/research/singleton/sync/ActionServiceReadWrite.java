package com.code.research.singleton.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public enum ActionServiceReadWrite {
    INSTANCE;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private long total;
    private String snapshot;

    public void write() {        // writer
        writeLock.lock();
        try{
            total ++;
            snapshot = "total=" + total;
        } finally {
            writeLock.unlock();
        }
    }

    public String read() {     // reader
        readLock.lock();
        try{
            return snapshot;
        } finally {
            readLock.unlock();
        }
    }
}
