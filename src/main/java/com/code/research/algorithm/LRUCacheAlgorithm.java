package com.code.research.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LRUCacheAlgorithm {
    private final int capacity;
    private final LinkedHashMap<Integer, Integer> map;

    public LRUCacheAlgorithm(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > LRUCacheAlgorithm.this.capacity; // evict LRU in O(1)
            }
        };
    }

    public Integer get(int key) {               // O(1) average
        return map.getOrDefault(key, null);     // also bumps to MRU if present
    }

    public void put(int key, int value) {       // O(1) average
        map.put(key, value);                    // may trigger eviction
    }

    public int size() { return map.size(); }
    public boolean containsKey(int key) { return map.containsKey(key); }

    public static void main(String[] args) {
        LRUCacheAlgorithm c = new LRUCacheAlgorithm(2);
        c.put(1, 10); c.put(2, 20);
        System.out.println(c.get(1)); // 10 (1 becomes MRU)
        c.put(3, 30);                 // evicts 2
        System.out.println(c.get(2)); // null
    }
}
