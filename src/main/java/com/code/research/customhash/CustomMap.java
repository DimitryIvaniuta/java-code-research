package com.code.research.customhash;

/**
 * CustomMap: simplified Map interface defining basic operations.
 */
public interface CustomMap<K, V> {

    /**
     * Associates the specified value with the specified key in this map.
     * @return the previous value associated with key, or null if there was none.
     */
    V put(K key, V value);

    /**
     * Returns the value to which the specified key is mapped, or null if none.
     */
    V get(K key);

    /**
     * Removes the mapping for a key.
     * @return the previous value associated with key, or null if there was none.
     */
    V remove(K key);

    /**
     * Returns the number of key-value mappings in this map.
     */
    int size();
}
