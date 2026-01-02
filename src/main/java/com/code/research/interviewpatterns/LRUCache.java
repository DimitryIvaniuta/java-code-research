package com.code.research.interviewpatterns;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe LRU cache.
 * <p>
 *
 * Talk track (say before coding, 15–20 sec)
 * “I will implement LRUCache<K,V> using LinkedHashMap with accessOrder=true.
 *  I will evict with removeEldestEntry when size exceeds capacity.
 *  For thread-safety I use one lock, because get() also updates access order.”
 *
 * <p>
 * Key points:
 * - Uses LinkedHashMap(accessOrder=true) to keep entries ordered by recent access.
 * - Evicts least-recently-used entry when size exceeds capacity.
 * - Single lock because get() also updates access order (so it's a "write").
 */
public final class LRUCache<K, V> {

    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();

    // LinkedHashMap is not thread-safe, we guard it with lock.
    private final LinkedHashMap<K, V> map;

    public LRUCache(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;

        // accessOrder=true => iteration order is LRU -> MRU
        this.map = new LinkedHashMap<>(Math.max(16, capacity), 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    /**
     * Returns value or null if missing. Updates access order when present.
     */
    public V get(K key) {
        Objects.requireNonNull(key, "key");
        lock.lock();
        try {
            return map.get(key);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Puts value. If key exists, updates value and makes it most-recently-used.
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "key");
        // value can be null if you want; if not, enforce non-null here
        lock.lock();
        try {
            map.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns true if key exists. (Also updates order because LinkedHashMap#get would.)
     */
    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "key");
        lock.lock();
        try {
            return map.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Current size.
     */
    public int size() {
        lock.lock();
        try {
            return map.size();
        } finally {
            lock.unlock();
        }
    }

    /**
     * For debugging/tests: returns keys in LRU -> MRU order.
     */
    public String keysInOrder() {
        lock.lock();
        try {
            return map.keySet().toString();
        } finally {
            lock.unlock();
        }
    }
}
