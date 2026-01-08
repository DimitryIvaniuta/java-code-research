package com.code.research.interviewpatterns;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

public final class LRUCacheString {
    private final int capacity;
    private final LinkedHashMap<Integer, String> cache;
    private final ReentrantLock lock;

    public LRUCacheString(final int capacity){
        this.capacity = capacity;
        cache = new LinkedHashMap<>(capacity);
        lock = new ReentrantLock();
    }

    public String get(final int key) {
        lock.lock();
        try {
            return cache.get(key);
        } finally {
            lock.unlock();
        }
    }

    public void put(final Integer key, final String value) {
        lock.lock();
        try{
            evictEldestEntry();
            cache.put(key, value);
        } finally {
            lock.unlock();
        }
    }
    public void delete(final int key) {
        lock.lock();
        try{
            cache.remove(key);
        } finally {
            lock.unlock();
        }

    }

    private void evictEldestEntry() {
        if(cache.size() == capacity){
            cache.pollFirstEntry();
        }
    }

    public static void main(String[] args) {
        LRUCacheString c = new LRUCacheString(5);
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
