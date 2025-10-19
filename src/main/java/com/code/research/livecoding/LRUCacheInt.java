package com.code.research.livecoding;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LRUCacheInt {
    private final int capacity;
    private final Map<Integer, Integer> map;

    public LRUCacheInt(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > LRUCacheInt.this.capacity;
            }
        };
    }

    public Integer get(int key) {
        return map.getOrDefault(key, null); // accessOrder bumps to MRU if present
    }

    public void put(int key, int value) {
        map.put(key, value); // may evict LRU via removeEldestEntry
    }

    public int size() { return map.size(); }
}
