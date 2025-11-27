package com.code.research.algorithm;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Frequently-used (MFU) cache with **top-eviction**:
 * - When capacity is exceeded, evict the *most frequently used* key.
 * - Tie-break within the same frequency by oldest (FIFO within that frequency).
 * - Thread-safe via ReentrantReadWriteLock (get/put mutate -> WRITE lock).
 *
 * API: capacity > 0, int key/int value for simplicity.
 * Time: O(1) average per get/put.
 */
public final class MFUCache {
    private static final class Node {
        int key;
        int value;
        int frequency;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    // max items
    private final int capacity;
    // key -> node (value + freq)
    private final Map<Integer, Node> nodes = new HashMap<>();
    private final Map<Integer, LinkedHashSet<Integer>> buckets = new HashMap<>();
    // freq -> keys with that frequency (LinkedHashSet preserves FIFO for tie-break)
    // current highest frequency in cache
    private int maxFreq = 0;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public MFUCache(int capacity) {
        if(capacity <= 0){
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
    }

    /**
     * Get value by key; returns optional null if absent. Also increases the key's frequency.
     * */
    public Optional<Integer> get(int key) {
        // frequency update mutates state -> WRITE
        lock.writeLock().lock();
        try {
            Node node = nodes.get(key);
            if (node != null) {
                // move key from fre    q f to f+1
                touch(node);
                return Optional.of(node.value);
            }
            // miss
            return Optional.empty();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Put (insert/update). On overflow, evict **most frequently used** entry.
     */
    public void put(int key, int value) {
        lock.writeLock().lock();
        try {
            Node node = nodes.get(key);
            // update existing -> bump frequency
            if(node != null) {
                node.value = value;
                touch(node);
                return;
            }
            // top-evict MFU
            if(nodes.size() == capacity){
                evictMostFrequent();
            }
            // insert new with freq=1
            Node newNode = new Node(key, value);
            nodes.put(key, newNode);
            buckets.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(newNode.key);
            // keep maxFreq correct
            if(maxFreq <= 0){
                maxFreq = 1;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /** Current number of items (read-only; READ lock is enough). */
    public int size() {
        lock.readLock().lock();
        try{
            return nodes.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Evict one key from the **most frequent** bucket (FIFO within that bucket).
     */
    private void evictMostFrequent() {
        // Move maxFreq down until we find a non-empty bucket
        while(maxFreq > 0
                && (!buckets.containsKey(maxFreq)
                        || buckets.get(maxFreq).isEmpty())
        ){
            maxFreq--;
        }
        // should not happen when size==capacity, but guard anyway
        if(maxFreq == 0) {
            return;
        }
        LinkedHashSet<Integer> set = buckets.get(maxFreq);
        // oldest key within the most-frequent bucket
        Integer victim = set.getFirst();
        set.remove(victim);
        if(set.isEmpty()){
            buckets.remove(maxFreq);
            // slide down to next non-empty freq if any
            decMaxFreq();
        }
        // remove node mapping
        nodes.remove(victim);
    }

    /**
     * Increase node's frequency: remove from old bucket, add to new, fix maxFreq.
     */
    private void touch(Node node) {
        int oldF = node.frequency;
        LinkedHashSet<Integer> oldSet = buckets.get(oldF);
        if(oldSet != null) {
            oldSet.remove(node.key);
            if(oldSet.isEmpty()) {
                buckets.remove(oldF);
                // if we emptied top bucket, slide maxFreq down
                if(oldF == maxFreq) {
                    decMaxFreq();
                }
            }
        }
        int newF = ++node.frequency;
        buckets.computeIfAbsent(newF, k -> new LinkedHashSet<>()).add(node.key);
        // new highest frequency reached
        if(newF > maxFreq) {
            maxFreq = newF;
        }
    }

    /**
     * Decrease maxFreq until it points to an existing non-empty bucket (or 0 if none).
     */
    private void decMaxFreq() {
        while(maxFreq > 0) {
            LinkedHashSet<Integer> s = buckets.get(maxFreq);
            if(s==null || s.isEmpty()) {
                break;
            }
            maxFreq--;
        }
    }

    // ---- tiny demo ----
    public static void main(String[] args) {
        MFUCache c = new MFUCache(2);
        c.put(1, 10);              // {1(freq=1)}
        c.put(2, 20);              // {1:1, 2:1}
        System.out.println(c.get(1)); // 10 -> freq(1)=2, maxFreq=2
        c.put(3, 30);              // capacity exceeded: evict MFU (key 1 with freq=2)
        System.out.println(c.get(1)); // -1 (evicted)
        System.out.println(c.get(2)); // 20 (now freq(2)=2, maxFreq=2)
        c.put(4, 40);              // evict MFU: key 2 (freq=2) > key 3 (freq=1)
        System.out.println(c.get(2)); // -1
        System.out.println(c.get(3)); // 30
        System.out.println(c.get(4)); // 40
    }
}
