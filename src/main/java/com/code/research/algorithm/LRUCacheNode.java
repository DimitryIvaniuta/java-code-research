package com.code.research.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU Cache with O(1) get/put and eviction.
 * Uses: HashMap<K,Node<K,V>> + doubly linked list (most-recent at head).
 */
public final class LRUCacheNode<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> index = new HashMap<>();
    // Sentinels: head.next = MRU, tail.prev = LRU
    private final Node<K, V> head = new Node<>(null, null);
    private final Node<K, V> tail = new Node<>(null, null);

    public LRUCacheNode(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int capacity() { return capacity; }
    public int size()     { return index.size(); }
    public boolean containsKey(K key) { return index.containsKey(key); }

    /**
     * Returns value or null if absent; marks entry as most recently used.
     */
    public V get(K key) {
        Node<K, V> node = index.get(key);
        if (node == null) return null;
        moveToFront(node);
        return node.value;
    }

    /**
     * Puts/updates the value; if capacity exceeded, evicts least recently used.
     * Returns previous value for key or null if none.
     */
    public V put(K key, V value) {
        Node<K, V> existing = index.get(key);
        if (existing != null) {
            V old = existing.value;
            existing.value = value;
            moveToFront(existing);
            return old;
        }

        Node<K, V> node = new Node<>(key, value);
        index.put(key, node);
        insertAfterHead(node);

        if (index.size() > capacity) {
            Node<K, V> lru = tail.prev;      // guaranteed non-sentinel
            removeNode(lru);
            index.remove(lru.key);
        }
        return null;
    }

    /**
     * Removes key if present; returns removed value or null.
     */
    public V remove(K key) {
        Node<K, V> node = index.remove(key);
        if (node == null) return null;
        removeNode(node);
        return node.value;
    }

    /**
     * Clears cache in O(n).
     */
    public void clear() {
        index.clear();
        head.next = tail;
        tail.prev = head;
    }

    // --- Doubly linked list helpers (all O(1)) ---

    private void moveToFront(Node<K, V> node) {
        removeNode(node);
        insertAfterHead(node);
    }

    private void insertAfterHead(Node<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = node.next = null; // help GC
    }

    private static final class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;
        Node(K key, V value) { this.key = key; this.value = value; }
    }

    // --- Minimal demo ---
    public static void main(String[] args) {
        LRUCacheNode<Integer, String> cache = new LRUCacheNode<>(2);
        cache.put(1, "A"); // [1:A]
        cache.put(2, "B"); // [2:B, 1:A]  (2 is MRU)
        System.out.println(cache.get(1)); // -> A, order: [1:A, 2:B]
        cache.put(3, "C"); // evicts LRU (key=2); cache: [3:C, 1:A]
        System.out.println(cache.get(2)); // -> null (evicted)
        cache.put(1, "A'"); // update & move to front: [1:A', 3:C]
        System.out.println(cache.get(1)); // -> A'
        System.out.println("size=" + cache.size()); // -> 2
    }
}
