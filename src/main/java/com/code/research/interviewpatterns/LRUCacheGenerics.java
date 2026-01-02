package com.code.research.interviewpatterns;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe LRU cache (generic).
 *
 * Variant features:
 * - Optional<V> getOptional(K key)
 * - remove(K key)
 *
 * Notes:
 * - Uses LinkedHashMap(accessOrder=true) so get/put update recency.
 * - Single lock because get() updates internal order => it's a "write".
 */
public final class LRUCacheGenerics<K, V> {

    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final LinkedHashMap<K, V> map;

    public LRUCacheGenerics(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;

        this.map = new LinkedHashMap<>(Math.max(16, capacity), 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCacheGenerics.this.capacity;
            }
        };
    }

    /** Returns value or null if missing. Updates access order when present. */
    public V get(K key) {
        Objects.requireNonNull(key, "key");
        lock.lock();
        try {
            return map.get(key);
        } finally {
            lock.unlock();
        }
    }

    /** Returns Optional.empty() if missing. Updates access order when present. */
    public Optional<V> getOptional(K key) {
        Objects.requireNonNull(key, "key");
        lock.lock();
        try {
            return Optional.ofNullable(map.get(key));
        } finally {
            lock.unlock();
        }
    }

    /** Puts value (value may be null if you allow it). */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "key");
        lock.lock();
        try {
            map.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    /** Removes key and returns removed value (Optional.empty() if missing). */
    public Optional<V> remove(K key) {
        Objects.requireNonNull(key, "key");
        lock.lock();
        try {
            return Optional.ofNullable(map.remove(key));
        } finally {
            lock.unlock();
        }
    }

    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "key");
        lock.lock();
        try {
            return map.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return map.size();
        } finally {
            lock.unlock();
        }
    }

    /** For debugging/tests: keys in LRU -> MRU order. */
    public String keysInOrder() {
        lock.lock();
        try {
            return map.keySet().toString();
        } finally {
            lock.unlock();
        }
    }
}
