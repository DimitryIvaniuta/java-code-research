package com.code.research.datastructures.hash.lfucache;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * LFUCache is a generic implementation of a Least Frequently Used (LFU) cache.
 * It supports get and put operations in O(1) time.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of values stored in the cache
 */
@Slf4j
public class LFUCacheImpl<K, V> {

    private final int capacity;

    private int minFreq;

    private final Map<K, NodeCache<K, V>> keyToNode;

    private final Map<Integer, LinkedHashSet<NodeCache<K, V>>> freqToNodes;

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
            NodeCache<K, V> node = keyToNode.get(key);
            node.setValue(value);
            updateFrequency(node);
            return;
        }
        if (keyToNode.size() >= capacity) {
            evictLFU();
        }
        NodeCache<K, V> newNode = new NodeCache<>(key, value);
        keyToNode.put(key, newNode);
        // Always initialize the set for frequency 1 if not present.
        freqToNodes.computeIfAbsent(1, ignore -> new LinkedHashSet<>()).add(newNode);
        minFreq = 1; // New node has frequency 1.
    }

    /**
     * Updates the frequency of a given node.
     *
     * @param node The node whose frequency is to be updated.
     */
    private void updateFrequency(NodeCache<K, V> node) {
        int freq = node.getFreq();
        // Remove the node from the current frequency group.
        LinkedHashSet<NodeCache<K, V>> nodesAtFreq = freqToNodes.get(freq);
        if (nodesAtFreq == null) {
            nodesAtFreq = new LinkedHashSet<>();
        }
        nodesAtFreq.remove(node);
        // If the current frequency group is empty, remove it and update minFreq if needed.
        if (nodesAtFreq.isEmpty()) {
            freqToNodes.remove(freq);
            if (minFreq == freq) {
                minFreq++;
            }
        }
        // Increment the node's frequency.
        node.increaseFreq();
        // Add the node to the new frequency group, ensuring the set is initialized.
        freqToNodes.computeIfAbsent(node.getFreq(), ignore -> new LinkedHashSet<>()).add(node);
    }

    /**
     * Evicts the least frequently used node from the cache.
     */
    private void evictLFU() {
        // Get the set of nodes with the minimum frequency.
        LinkedHashSet<NodeCache<K, V>> nodesAtMinFreq = freqToNodes.get(minFreq);
        if (nodesAtMinFreq == null || nodesAtMinFreq.isEmpty()) {
            return; // This should not happen if the cache is non-empty.
        }
        // Evict the first node inserted (FIFO order) in the min frequency set.
        NodeCache<K, V> nodeToEvict = nodesAtMinFreq.getFirst();
        nodesAtMinFreq.remove(nodeToEvict);
        if (nodesAtMinFreq.isEmpty()) {
            freqToNodes.remove(minFreq);
        }
        keyToNode.remove(nodeToEvict.getKey());
    }

    /**
     * For debugging: Prints the current cache content.
     */
    public void printCache() {
        log.info("Cache contents:");
        for (Map.Entry<K, NodeCache<K, V>> entry : keyToNode.entrySet()) {
            NodeCache<K, V> node = entry.getValue();
            log.info("Key: {}, Value: {}, Frequency: {}", node.getKey(), node.getValue(), node.getFreq());
        }
    }
}
