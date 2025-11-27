package com.code.research.algorithm;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * MFU (Most-Frequently-Used) cache with TOP eviction.
 * Data structures:
 * - HashMap<Integer, Node> index: key -> node        (O(1) lookup)
 * - TreeSet<Node> order: auto-sorted by (freq DESC, time ASC, key ASC)
 * * Smallest element in the set is the eviction victim:
 * highest frequency, and among those the oldest (FIFO).
 * - Monotonic 'tick' used to stamp insertion into the *current* frequency.
 * <p>
 * Complexity: get/put O(log N) due to TreeSet re-ordering. Space O(N).
 */
public final class MFUCachePriority {
    private static final class Node {
        final int key;
        int value;
        int freq;        // usage count
        long time;       // arrival time into this freq bucket (for FIFO tie-break)

        Node(int key, int value, int freq, long time) {
            this.key = key;
            this.value = value;
            this.freq = freq;
            this.time = time;
        }
    }

    // Comparator: higher freq first, then older first, then smaller key
    private static final Comparator<Node> CMP = (a, b) -> {
        if (a.freq != b.freq) {
            return Integer.compare(b.freq, a.freq);     // freq DESC
        }
        if (a.time != b.time) {
            return Long.compare(a.time, b.time);        // older (smaller time) first
        }
        return Integer.compare(a.key, b.key);                              // total order
    };

    private final int capacity;
    private final Map<Integer, Node> index = new HashMap<>();
    private final TreeSet<Node> order = new TreeSet<>(CMP);
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private long tick = 0;  // monotonically increasing timestamp

    public MFUCachePriority(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("capacity must be >= 0");
        this.capacity = capacity;
    }

    /**
     * Get value; increases frequency (so needs WRITE lock). Returns -1 if absent.
     */
    public int get(int key) {
        rw.writeLock().lock();
        try {
            Node n = index.get(key);
            if (n == null) return -1;
            // re-rank: remove → mutate → reinsert
            order.remove(n);
            n.freq += 1;
            n.time = ++tick;          // new arrival into new freq: becomes "newest" within that freq
            order.add(n);
            return n.value;
        } finally {
            rw.writeLock().unlock();
        }
    }

    /**
     * Put value; on overflow, evict the MFU (top) entry.
     */
    public void put(int key, int value) {
        if (capacity == 0) return;
        rw.writeLock().lock();
        try {
            Node n = index.get(key);
            if (n != null) {
                // update existing value and bump freq
                order.remove(n);
                n.value = value;
                n.freq += 1;
                n.time = ++tick;
                order.add(n);
                return;
            }
            // need space?
            if (index.size() == capacity) {
                Node victim = order.first(); // highest freq, oldest among those
                order.remove(victim);
                index.remove(victim.key);
            }
            Node nn = new Node(key, value, 1, ++tick);
            index.put(key, nn);
            order.add(nn);
        } finally {
            rw.writeLock().unlock();
        }
    }

    /**
     * Current size (read-only).
     */
    public int size() {
        rw.readLock().lock();
        try {
            return index.size();
        } finally {
            rw.readLock().unlock();
        }
    }

    // --- tiny demo ---
    public static void main(String[] args) {
        MFUCachePriority c = new MFUCachePriority(2);
        c.put(1, 10);                 // (1,f=1)
        c.put(2, 20);                 // (1,f=1),(2,f=1)
        System.out.println(c.get(1)); // 10 -> (1,f=2)
        c.put(3, 30);                 // capacity full -> evict MFU: key 1 (f=2)
        System.out.println(c.get(1)); // -1
        System.out.println(c.get(2)); // 20 -> (2,f=2)
        c.put(4, 40);                 // evict MFU: key 2 (f=2) over key 3 (f=1)
        System.out.println(c.get(2)); // -1
        System.out.println(c.get(3)); // 30
        System.out.println(c.get(4)); // 40
    }
}
