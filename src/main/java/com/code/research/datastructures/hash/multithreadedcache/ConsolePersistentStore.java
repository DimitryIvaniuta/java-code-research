package com.code.research.datastructures.hash.multithreadedcache;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConsolePersistentStore is a simple implementation of PersistentStore that
 * simulates persistent storage using an internal ConcurrentHashMap.
 *
 * @param <K> the key type.
 * @param <V> the value type.
 */
@Slf4j
public class ConsolePersistentStore<K, V> implements PersistentStore<K, V> {

    /**
     * Internal store that simulates persistent storage.
     */
    private final ConcurrentHashMap<K, V> store = new ConcurrentHashMap<>();

    @Override
    public void write(K key, V value) {
        store.put(key, value);
        log.info("Persisting key: {}, value: {}", key, value);
    }

    @Override
    public V read(K key) {
        V value = store.get(key);
        log.info("Reading key: {}, value: {}", key, value);
        return value;
    }
}