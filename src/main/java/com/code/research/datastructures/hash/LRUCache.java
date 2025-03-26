package com.code.research.datastructures.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * A generic implementation of an LRU (Least Recently Used) Cache.
 * The cache uses a HashMap for O(1) lookups and a doubly linked list to track
 * the order of use from most recently used (MRU) to least recently used (LRU).
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
@Slf4j
public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head;
    private final Node<K, V> tail;

    /**
     * Node class for doubly linked list.
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Constructs an LRUCache with the specified capacity.
     *
     * @param capacity the maximum number of entries the cache can hold
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        // Initialize dummy head and tail nodes to simplify edge-case handling.
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Retrieves the value associated with the specified key.
     * If the key exists, the node is moved to the head (most recently used).
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or null if not found
     */
    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }
        // Move the accessed node to the head.
        moveToHead(node);
        return node.value;
    }

    /**
     * Inserts or updates the key-value pair in the cache.
     * If the cache exceeds its capacity, the least recently used entry is evicted.
     *
     * @param key   the key of the entry
     * @param value the value associated with the key
     */
    public void put(K key, V value) {
        Node<K, V> node = cache.get(key);
        if (node != null) {
            // Update value and move node to head.
            node.value = value;
            moveToHead(node);
        } else {
            // Create new node.
            Node<K, V> newNode = new Node<>(key, value);
            cache.put(key, newNode);
            addNode(newNode);
            if (cache.size() > capacity) {
                // Evict the least recently used entry.
                Node<K, V> tailNode = popTail();
                cache.remove(tailNode.key);
            }
        }
    }

    /**
     * Adds a new node right after the head.
     *
     * @param node the node to add
     */
    private void addNode(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * Removes a node from the doubly linked list.
     *
     * @param node the node to remove
     */
    private void removeNode(Node<K, V> node) {
        Node<K, V> prevNode = node.prev;
        Node<K, V> nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    /**
     * Moves a given node to the head (marking it as most recently used).
     *
     * @param node the node to move
     */
    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addNode(node);
    }

    /**
     * Pops the least recently used node from the end of the list.
     *
     * @return the node that was removed
     */
    private Node<K, V> popTail() {
        Node<K, V> res = tail.prev;
        removeNode(res);
        return res;
    }

    /**
     * Returns a string representation of the cache's current state.
     *
     * @return a string showing keys in order from most to least recently used
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<K, V> curr = head.next;
        while (curr != tail) {
            sb.append(curr.key).append(":").append(curr.value).append(" -> ");
            curr = curr.next;
        }
        sb.append("NULL");
        return sb.toString();
    }

    /**
     * Main method demonstrating the usage of the LRUCache.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        log.info("Initial Cache: " + cache);

        cache.put(1, "Apple");
        cache.put(2, "Banana");
        cache.put(3, "Cherry");
        log.info("Cache after 3 inserts: {}", cache);

        // Access key 2 so that it becomes most recently used.
        log.info("Get key 2: {}", cache.get(2));
        log.info("Cache after accessing key 2: {}", cache);

        // Insert a new key, which should evict the least recently used (key 1).
        cache.put(4, "Date");
        log.info("Cache after inserting key 4: {}", cache);
        log.info("Get key 1 (should be null): {}", cache.get(1));
    }

}
