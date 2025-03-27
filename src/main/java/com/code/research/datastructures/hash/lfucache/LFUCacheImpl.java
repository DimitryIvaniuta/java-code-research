package com.code.research.datastructures.hash.lfucache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LFUCache is a generic implementation of a Least Frequently Used (LFU) cache.
 * It supports get and put operations in O(1) time.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of values stored in the cache
 */

public class LFUCacheImpl<K, V> {

    private final int capacity;

    private int minFreq;

    private final Map<K, NodeCache<K, V>> keyToNode;

    private final Map<Integer, LinkedHashMap<K, NodeCache<K, V>>> freqToNodes;

    /**
     * Constructs an LFUCacheImpl with the specified capacity.
     *
     * @param capacity the maximum number of entries the cache can hold
     */
    public LFUCacheImpl(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.keyToNode = new HashMap<>();
        this.freqToNodes = new HashMap<>();
    }

    /**
     * Retrieves the value associated with the given key if present in the cache.
     * This operation updates the frequency of the node.
     *
     * @param key the key whose associated value is to be returned
     * @return the value if found; otherwise, returns null
     */
    public V get(K key) {
        if (!keyToNode.containsKey(key)) {
            return null;
        }
        NodeCache<K, V> node = keyToNode.get(key);
        updateFrequency(node);
        return node.getValue();
    }

    /**
     * Inserts or updates the value for the specified key.
     * If the cache exceeds its capacity, the least frequently used entry is evicted.
     *
     * @param key   the key to insert or update
     * @param value the value associated with the key
     */
    public void put(K key, V value) {
        if (capacity <= 0) {
            return;
        }
        if (keyToNode.containsKey(key)) {
            // Update the existing node's value and frequency.
            NodeCache<K, V> node = keyToNode.get(key);
            node.setValue(value);
            updateFrequency(node);
        } else {
            // Evict LFU element if capacity is reached.
            if (keyToNode.size() >= capacity) {
                evictLFU();
            }
            // Insert the new node.
            NodeCache<K, V> newNode = new NodeCache<>(key, value);
            keyToNode.put(key, newNode);
            freqToNodes.computeIfAbsent(1, ignore -> new LinkedHashMap<>()).put(key, newNode);
            minFreq = 1;
        }
    }

    /**
     * Updates the frequency of the specified node after an access.
     * Moves the node from its current frequency list to the next frequency list.
     *
     * @param node the node whose frequency is to be updated
     */
    private void updateFrequency(NodeCache<K, V> node) {
        int freq = node.getFreq();
        LinkedHashMap<K, NodeCache<K, V>> nodesAtFreq = freqToNodes.get(freq);
        nodesAtFreq.remove(node.getKey());

        // Remove the frequency group if empty, and update minFreq if needed.
        if (nodesAtFreq.isEmpty()) {
            freqToNodes.remove(freq);
            if (minFreq == freq) {
                minFreq++;
            }
        }
        // Increase node frequency.
        node.increaseFreq();
        freqToNodes.computeIfAbsent(node.getFreq(), ignore -> new LinkedHashMap<>())
                .put(node.getKey(), node);
    }

    /**
     * Evicts the least frequently used node from the cache.
     * If multiple nodes have the same frequency, the least recently used one is removed.
     */
    private void evictLFU() {
        LinkedHashMap<K, NodeCache<K, V>> nodesAtMinFreq = freqToNodes.get(minFreq);
        // The first key in the LinkedHashMap is the least recently used.
        K evictKey = nodesAtMinFreq.entrySet().iterator().next().getKey();
        nodesAtMinFreq.remove(evictKey);
        if (nodesAtMinFreq.isEmpty()) {
            freqToNodes.remove(minFreq);
        }
        keyToNode.remove(evictKey);
    }

}
