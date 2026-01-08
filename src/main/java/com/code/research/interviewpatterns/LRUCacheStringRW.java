package com.code.research.interviewpatterns;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class LRUCacheStringRW {
    private final int capacity;
    // accessOrder=true → access (get/put) moves entry to MRU
    private final LinkedHashMap<Integer, String> map;
    private final ReentrantReadWriteLock rw;

    public LRUCacheStringRW(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(Math.min(capacity, 16), 0.75f, true);
        this.rw = new ReentrantReadWriteLock();
    }

    /**
     * Mutating get (promotes to MRU) → WRITE lock. Returns null if absent.
     */
    public String get(int key) {
        rw.writeLock().lock();
        try {
            return map.get(key); // accessOrder bumps if present
        } finally {
            rw.writeLock().unlock();
        }
    }

    /**
     * Non-mutating peek (does NOT change recency) → READ lock.
     */
    public String peek(int key) {
        rw.readLock().lock();
        try {
            return map.get(key); // read-only
        } finally {
            rw.readLock().unlock();
        }
    }

    /**
     * Put/update; evicts LRU when size exceeds capacity → WRITE lock.
     */
    public void put(int key, String value) {
        rw.writeLock().lock();
        try {
            map.put(key, value);                  // becomes MRU
            if (map.size() > capacity) {          // manual eviction (no override)
                Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
                it.next();                         // first = LRU in access-order map
                it.remove();                       // evict LRU
            }
        } finally {
            rw.writeLock().unlock();
        }
    }

    /**
     * Remove key if present.
     */
    public void remove(int key) {
        rw.writeLock().lock();
        try {
            map.remove(key);
        } finally {
            rw.writeLock().unlock();
        }
    }

    /**
     * Current size (read-only).
     */
    public int size() {
        rw.readLock().lock();
        try {
            return map.size();
        } finally {
            rw.readLock().unlock();
        }
    }

    // tiny demo
    public static void main(String[] args) {
        LRUCacheStringRW c = new LRUCacheStringRW(2);
        c.put(1, "A");
        c.put(2, "B");        // {1=A,2=B}
        System.out.println(c.peek(1));     // A (doesn't affect recency)
        System.out.println(c.get(1));      // A (now 1 is MRU)
        c.put(3, "C");                      // evict LRU=2 → {1=A,3=C}
        System.out.println(c.get(2));      // null
        System.out.println(c.get(1));      // A
        System.out.println(c.get(3));      // C
        System.out.println("size=" + c.size()); // 2
    }
}
