package com.code.research.datastructures.hash.lfucache;

import lombok.Getter;
import lombok.Setter;

/**
 * Node class represents each entry in the cache. It stores the key, value,
 * and the frequency of accesses.
 *
 * @param <K> key type
 * @param <V> value type
 */
@Getter
public class NodeCache<K, V> {


    private K key;

    @Setter
    private V value;

    @Getter
    private int freq;

    public NodeCache(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    public void increaseFreq() {
        this.freq++;
    }

}
