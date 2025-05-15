package com.code.research.threads.completablefuture;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryBuilder implements ThreadFactory {
    private final AtomicInteger idx = new AtomicInteger();
    private final String pattern;
    private final boolean daemon;

    ThreadFactoryBuilder(String pattern, boolean daemon) {
        this.pattern = pattern;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, String.format(pattern, idx.incrementAndGet()));
        t.setDaemon(daemon);
        return t;
    }
}