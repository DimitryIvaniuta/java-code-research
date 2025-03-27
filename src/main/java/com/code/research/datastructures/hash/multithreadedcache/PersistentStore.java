package com.code.research.datastructures.hash.multithreadedcache;

/**
 * PersistentStore represents a generic interface for persistent storage,
 * supporting both write and read operations.
 *
 * @param <K> the key type.
 * @param <V> the value type.
 */
public interface PersistentStore<K, V> {
    
    /**
     * Writes the given key-value pair to persistent storage.
     *
     * @param key   the key to write.
     * @param value the value associated with the key.
     */
    void write(K key, V value);

    /**
     * Reads the value for the given key from persistent storage.
     *
     * @param key the key to read.
     * @return the value associated with the key, or null if not found.
     */
    V read(K key);

}
