package com.code.research.algorithm;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class LRUCache {

    private int cap;
    private Map<Integer, Integer> map;
    private ReentrantReadWriteLock lock;
    public LRUCache(int capacity) {
        this.cap = capacity;
        this.lock = new ReentrantReadWriteLock();
        this.map = new LinkedHashMap<>(14, .75f, true);
    }

    public LRUCache(int capacity, boolean b2) {
        this.cap = capacity;
        this.map = new LinkedHashMap<>(16, .75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> e) {
                return size() > cap;
            }
        };

    }

    public int get(int key) {
        lock.readLock().lock();
        Integer v = map.get(key);
        try{
            return v == null ? -1 : v;
        } finally {
            lock.readLock().unlock();
        }

    }

    public void put(int key, int value) {
        lock.writeLock().lock();
        try{
            map.put(key, value);
            if(map.size() > cap){
                Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
                it.next();
                it.remove();
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try { return map.size(); }
        finally { lock.readLock().unlock(); }
    }

    public boolean containsKey(int key) {
        lock.readLock().lock();
        try { return map.containsKey(key); }            // does NOT change recency
        finally { lock.readLock().unlock(); }
    }
}
