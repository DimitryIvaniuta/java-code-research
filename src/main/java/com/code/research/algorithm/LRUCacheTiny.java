package com.code.research.algorithm;

import java.util.LinkedHashMap;

public class LRUCacheTiny {
    // max size
    private final int cap;
    // true ⇒ move on access
    private final LinkedHashMap<Integer, Integer> m =
            new LinkedHashMap<>(16, 0.75f, true);

    public LRUCacheTiny(int capacity) {
        this.cap = capacity;
    }

    // returns -1 if not found
    public int get(int k) {
        // also bumps recency
        Integer v = m.get(k);
        return v == null ? -1 : v;
    }

    public void put(int k, int v) {
        // insert/update
        m.put(k, v);
        // evict LRU (first entry)
        if (m.size() > cap) {
            var it = m.entrySet().iterator();
            it.next();
            it.remove();
        }
    }
}
