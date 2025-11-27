package com.code.research.algorithm;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread-safe LRU cache (evicts Least-Recently-Used entry).
 * - Uses LinkedHashMap with accessOrder=true to track recency.
 * - get/put mutate recency ⇒ guarded by WRITE lock.
 * - size() is read-only ⇒ guarded by READ lock.
 * Keys/values are ints for simplicity; adapt generics if needed.
 */
public final class LRUCacheRW {
    private final int cap;                                         // max entries
    private final LinkedHashMap<Integer, Integer> map =
            new LinkedHashMap<>(16, 0.75f, true);                  // accessOrder = true
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    public LRUCacheRW(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.cap = capacity;
    }

    /**
     * O(1) avg. Returns -1 if missing. Also promotes entry to MRU (mutates) → WRITE lock.
     */
    public int get(int key) {
        rw.writeLock().lock();
        try {
            Integer v = map.get(key);                              // moves to MRU if present
            return v == null ? -1 : v;
        } finally {
            rw.writeLock().unlock();
        }
    }

    /**
     * O(1) avg. Insert/update; evicts LRU if over capacity (first entry in iteration).
     */
    public void put(int key, int value) {
        rw.writeLock().lock();
        try {
            map.put(key, value);                                   // insert/update → becomes MRU
            if (map.size() > cap) {                                // manual eviction (no override)
                Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
                it.next();                                         // first = LRU in access-order map
                it.remove();                                       // drop LRU
            }
        } finally {
            rw.writeLock().unlock();
        }
    }

    /**
     * Read-only size (does not change recency) → READ lock.
     */
    public int size() {
        rw.readLock().lock();
        try {
            return map.size();
        } finally {
            rw.readLock().unlock();
        }
    }

    /**
     * Optional: non-mutating peek (does NOT change recency).
     */
    public int peek(int key) {
        rw.readLock().lock();
        try {
            return map.getOrDefault(key, -1);
        } finally {
            rw.readLock().unlock();
        }
    }

    // tiny demo
    public static void main(String[] args) {
        LRUCacheRW c = new LRUCacheRW(2);
        c.put(1, 1);                  // {1=1}
        c.put(2, 2);                  // {1=1, 2=2}
        System.out.println(c.get(1)); // 1  -> recency: {2, 1}
        c.put(3, 3);                  // evict LRU=2 -> {1=1, 3=3}
        System.out.println(c.get(2)); // -1
        c.put(4, 4);                  // evict LRU=1 -> {3=3, 4=4}
        System.out.println(c.get(1)); // -1
        System.out.println(c.get(3)); // 3
        System.out.println(c.get(4)); // 4
    }
}
