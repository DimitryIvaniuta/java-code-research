package com.code.research.datastructures.graph;

/**
 * A simple generic pair class to hold two related objects.
 *
 * @param <K> the type of the first element (key)
 * @param <V> the type of the second element (value)
 */
public class Pair<K, V> {

    /**
     * The first element of the pair.
     */
    private final K key;

    /**
     * The second element of the pair.
     */
    private final V value;

    /**
     * Constructs a pair with the specified key and value.
     *
     * @param key   the key
     * @param value the value
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of the pair.
     *
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns the value of the pair.
     *
     * @return the value
     */
    public V getValue() {
        return value;
    }
}