package com.code.research.algorithm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public final class EventCounter {
    private final ConcurrentHashMap<String, LongAdder> byKey = new ConcurrentHashMap<>();
    private final LongAdder total = new LongAdder();

    // O(1), contention-friendly
    public void inc(String key) {
        byKey.computeIfAbsent(key, k -> new LongAdder()).increment();
        total.increment();
    }

    public long get(String key) {
        LongAdder a = byKey.get(key);
        return a == null ? 0L : a.sum();
    }

    public long total() {
        return total.sum();
    }

    public static void main(String[] args) throws InterruptedException {
        EventCounter ec = new EventCounter();
        int threads = 8, perThread = 100_000;
        String[] keys = {"login", "purchase", "view"};

        try (ExecutorService pool = Executors.newFixedThreadPool(threads)) {
            CountDownLatch latch = new CountDownLatch(threads);
            for (int t = 0; t < threads; t++) {
                pool.execute(() -> {
                    for (int i = 0; i < perThread; i++) ec.inc(keys[i % keys.length]);
                    latch.countDown();
                });
            }
            latch.await(); // wait for tasks to finish
        }

        System.out.println("login   = " + ec.get("login"));
        System.out.println("purchase= " + ec.get("purchase"));
        System.out.println("view    = " + ec.get("view"));
        System.out.println("total   = " + ec.total());
        // Expect: total == threads * perThread
    }
}
