package com.code.research.algorithm;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class LRUCacheRW2Top {
    private final int cap;
    // accessOrder=true already moves items on access, but we'll force it explicitly too.
    private final LinkedHashMap<Integer, Integer> map =
            new LinkedHashMap<>(16, 0.75f, true);
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    public LRUCacheRW2Top(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.cap = capacity;
    }

    /** Get value and move this key to the most-recent position (MRU). */
    public int get(int key) {
        rw.writeLock().lock();                      // recency changes ⇒ WRITE lock
        try {
            Integer val = map.remove(key);          // remove if present (null if miss)
            if (val == null) return -1;             // miss
            map.put(key, val);                      // reinsert ⇒ becomes MRU explicitly
            return val;
        } finally {
            rw.writeLock().unlock();
        }
    }

    /** Put value; evict LRU on overflow. */
    public void put(int key, int value) {
        rw.writeLock().lock();
        try {
            // overwrite if exists (and place as MRU)
            if (map.containsKey(key)) {
                map.remove(key);
            }
            map.put(key, value);
            if (map.size() > cap) {
                Iterator<Map.Entry<Integer,Integer>> it = map.entrySet().iterator();
                it.next();                           // first = LRU
                it.remove();                         // evict LRU
            }
        } finally {
            rw.writeLock().unlock();
        }
    }

    /** Non-mutating peek (does NOT affect recency). */
    public int peek(int key) {
        rw.readLock().lock();
        try { return map.getOrDefault(key, -1); }
        finally { rw.readLock().unlock(); }
    }

    public int size() {
        rw.readLock().lock();
        try { return map.size(); }
        finally { rw.readLock().unlock(); }
    }
}
