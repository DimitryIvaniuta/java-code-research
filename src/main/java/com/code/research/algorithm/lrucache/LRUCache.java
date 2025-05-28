package com.code.research.algorithm.lrucache;

import java.util.LinkedHashMap;

/**
 * LRUCache implements a fixed-capacity cache with Least-Recently-Used eviction policy.
 * We achieve O(1) time for both get() and put() operations by combining:
 *  1. A HashMap for direct key→node lookup.
 *  2. A doubly-linked list to track usage order, with head = most-recently-used, tail = least-recently-used.
 */
public class LRUCache {
    // maximum number of entries in the cache
    private int capacity;
    // maps keys to their corresponding list node
    private final LinkedHashMap<Integer, LRUCacheNode> cache;
    // dummy head of the doubly-linked list
    private LRUCacheNode head;
    // dummy tail of the doubly-linked list
    private LRUCacheNode tail;

    /**
     * Initialize the LRUCache with the given capacity.
     * We create two dummy (sentinel) nodes head and tail to avoid null checks
     * when adding/removing real nodes at the ends.
     *
     * @param capacity maximum number of items the cache can hold
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>();

        // Create sentinels
        this.head = new LRUCacheNode(0, 0);
        this.tail = new LRUCacheNode(0, 0);

        // Link head <-> tail
        this.head.next = tail;
        this.tail.next = head;
    }

    /**
     * Retrieve the value associated with the given key, if present.
     * Moves the accessed node to the head of the list (marking it most-recently-used).
     *
     * @param key key whose value to retrieve
     * @return the value if found; -1 otherwise
     */
    public int get(int key) {
        LRUCacheNode node = cache.get(key);
        if(node == null) {
            return -1;
        }

        // Cache hit: move node to head (most recently used)
        moveToHead(node);
        return node.getValue();
    }

    /**
     * Insert or update the key/value pair.
     * If the key already exists, update its value and mark it as most-recently-used.
     * If the key is new, create a node and insert at head.
     * If the cache exceeds capacity, evict the least-recently-used node at tail.
     *
     * @param key   key to insert or update
     * @param value value to associate with the key
     */
    public void put(int key, int value) {
        LRUCacheNode node = cache.get(key);
        if(node != null) {
            // Key exists: update value and move to head
            node.setValue(value);
            moveToHead(node);
        } else {
            // Key does not exist: create new node
            LRUCacheNode newNode = new LRUCacheNode(key, value);
            // Add to hash map and list
            cache.put(key, newNode);
            addToHead(newNode);

            // If over capacity, remove LRU entry
            if(cache.size() > capacity) {
                // tail.prev is the least-recently-used real node
                LRUCacheNode lruPrev = tail.getPrev();
                // unlink from list
                removeNode(lruPrev);
                // remove from map
                cache.remove(lruPrev.getKey());
            }
        }
    }

    /**
     * Remove a node from its current position in the doubly-linked list.
     * @param node the node to unlink
     */
    private void removeNode(LRUCacheNode node) {
        LRUCacheNode prev = node.getPrev();
        LRUCacheNode next = node.getNext();
        // Bypass the node
        prev.setNext(next);
        prev.setPrev(next);
    }

    /**
     * Insert a node right after the head sentinel.
     * Marks this node as most-recently-used.
     * @param node the node to insert
     */
    private void addToHead(LRUCacheNode node) {
        LRUCacheNode firstReal = head.getNext();
        // Link head → node → firstReal
        head.setNext(node);
        node.setPrev(head);
        node.setNext(firstReal);
        firstReal.setPrev(node);
    }

    /**
     * Move an existing node to the head (most-recently-used position).
     * This is done by unlinking it then relinking at head.
     * @param node the node to move
     */
    private void moveToHead(LRUCacheNode node) {
        removeNode(node);
        addToHead(node);
    }
}
