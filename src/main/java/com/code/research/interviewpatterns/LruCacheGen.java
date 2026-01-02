package com.code.research.interviewpatterns;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LruCacheGen<K, V> {
    private final int capacity;
    private final Map<K, V> map;

    public LruCacheGen(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;

        // accessOrder=true -> iteration order becomes LRU -> MRU
        this.map = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LruCacheGen.this.capacity;
            }
        };
    }

    public synchronized V get(K key) {
        return map.get(key); // updates access order automatically
    }

    public synchronized void put(K key, V value) {
        map.put(key, value); // updates access order automatically
    }

    public synchronized int size() {
        return map.size();
    }

    // quick demo
    public static void main(String[] args) {
        LruCacheGen<Integer, String> c = new LruCacheGen<>(5);
        c.put(1, "A");
        c.put(2, "B");
        c.put(3, "C");
        c.put(4, "D");
        c.put(5, "E");
        c.get(2);                 // 2 becomes most recent
        c.put(6, "F");             // evicts 1 (least recent)
        System.out.println(c.get(1)); // null
        System.out.println(c.get(2)); // B
    }
}
