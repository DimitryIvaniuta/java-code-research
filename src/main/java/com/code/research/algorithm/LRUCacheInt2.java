package com.code.research.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU cache with Integer key/value.
 * O(1) get/put via HashMap + custom doubly linked list.
 */
public final class LRUCacheInt2 {
    private final int capacity;
    private final Map<Integer, Node> index = new HashMap<>();
    private final Node head = new Node(null, null); // MRU side
    private final Node tail = new Node(null, null); // LRU side

    public LRUCacheInt2(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int capacity() { return capacity; }
    public int size()     { return index.size(); }

    /** Returns cached value or null if absent; marks as most-recently used. */
    public Integer get(Integer key) {
        Node n = index.get(key);
        if (n == null) return null;
        moveToFront(n);
        return n.value;
    }

    /** Inserts/updates value; evicts least-recently used if over capacity. */
    public void put(Integer key, Integer value) {
        Node n = index.get(key);
        if (n != null) {
            n.value = value;
            moveToFront(n);
            return;
        }
        Node created = new Node(key, value);
        index.put(key, created);
        insertAfterHead(created);
        if (index.size() > capacity) {
            Node lru = tail.prev;         // guaranteed data node
            removeNode(lru);
            index.remove(lru.key);
        }
    }

    /** Removes key if present; returns removed value or null. */
    public Integer remove(Integer key) {
        Node n = index.remove(key);
        if (n == null) return null;
        removeNode(n);
        return n.value;
    }

    /** Clears cache. */
    public void clear() {
        index.clear();
        head.next = tail;
        tail.prev = head;
    }

    // --------- Doubly linked list helpers (all O(1)) ---------

    private void moveToFront(Node n) {
        removeNode(n);
        insertAfterHead(n);
    }

    private void insertAfterHead(Node n) {
        n.prev = head;
        n.next = head.next;
        head.next.prev = n;
        head.next = n;
    }

    private void removeNode(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
        n.prev = n.next = null; // help GC
    }

    private static final class Node {
        Integer key;
        Integer value;
        Node prev, next;
        Node(Integer key, Integer value) { this.key = key; this.value = value; }
    }

    // ---- Tiny demo ----
    public static void main(String[] args) {
        LRUCacheInt cache = new LRUCacheInt(2);
        cache.put(1, 10); // [1=10]
        cache.put(2, 20); // [2=20, 1=10]
        System.out.println(cache.get(1)); // -> 10, order [1=10, 2=20]
        cache.put(3, 30); // evicts 2 -> [3=30, 1=10]
        System.out.println(cache.get(2)); // -> null
        cache.put(1, 100); // update & move front -> [1=100, 3=30]
        System.out.println(cache.get(1)); // -> 100
        System.out.println("size=" + cache.size()); // 2
    }
}
