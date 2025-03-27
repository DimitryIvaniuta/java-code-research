package com.code.research.datastructures.hash.multithreadedcache;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * MultiThreadedWriteThroughCache is a thread-safe cache that implements write-through and read-through policies.
 * It uses a ConcurrentHashMap to hold cache entries and periodically flushes dirty entries to a persistent store
 * using a background thread.
 *
 * @param <K> the type of keys maintained by this cache.
 * @param <V> the type of values stored in this cache.
 */
@Slf4j
public class MultiThreadedWriteThroughCache<K, V> implements AutoCloseable {

    /**
     * The main cache storage for key-value pairs.
     */
    private final ConcurrentHashMap<K, V> cache;

    /**
     * Tracks keys that have been updated but not yet flushed to the persistent store.
     */
    private final ConcurrentHashMap<K, Boolean> dirtyKeys;

    /**
     * The persistent store to which cache entries are written.
     */
    private final PersistentStore<K, V> persistentStore;

    /**
     * ScheduledExecutorService for executing periodic flush tasks.
     */
    private final ScheduledExecutorService scheduler;

    /**
     * Constructs a MultiThreadedWriteThroughCache with the specified persistent store and flush interval.
     *
     * @param persistentStore the persistent store to write data to.
     * @param flushInterval   the interval in seconds at which the cache flushes updates.
     */
    public MultiThreadedWriteThroughCache(PersistentStore<K, V> persistentStore, long flushInterval) {
        this.cache = new ConcurrentHashMap<>();
        this.dirtyKeys = new ConcurrentHashMap<>();
        this.persistentStore = persistentStore;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        // Use flushInterval directly to schedule periodic flush tasks.
        scheduler.scheduleAtFixedRate(this::flush, flushInterval, flushInterval, TimeUnit.SECONDS);
    }

    /**
     * Retrieves the value associated with the given key from the cache.
     * Implements read-through behavior: if the key is not present in the cache,
     * attempts to load it from the persistent store.
     *
     * @param key the key whose value is to be returned.
     * @return the cached value, or null if not found in both cache and persistent store.
     */
    public V get(K key) {
        V value = cache.get(key);
        if (value == null) {
            value = persistentStore.read(key);
            if (value != null) {
                cache.put(key, value);
            }
        }
        return value;
    }

    /**
     * Inserts or updates the value associated with the given key in the cache.
     * Marks the key as dirty so that the change is eventually written to the persistent store.
     *
     * @param key   the key to insert or update.
     * @param value the value to associate with the key.
     */
    public void put(K key, V value) {
        cache.put(key, value);
        dirtyKeys.put(key, Boolean.TRUE);
    }

    /**
     * Flushes all dirty entries from the cache to the persistent store.
     * For each dirty key, writes the current value to persistent storage and then removes the key from the dirty set.
     */
    public void flush() {
        for (K key : dirtyKeys.keySet()) {
            V value = cache.get(key);
            if (value != null) {
                try {
                    persistentStore.write(key, value);
                } catch (Exception e) {
                    log.error("Failed to persist key: {}, value: {}. Error: {}", key, value, e.getMessage());
                }
            }
            dirtyKeys.remove(key);
        }
    }

    /**
     * Closes the cache by flushing any pending updates and shutting down the background scheduler.
     */
    @Override
    public void close() {
        try {
            flush();
        } finally {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                    if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                        log.error("Scheduler did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
