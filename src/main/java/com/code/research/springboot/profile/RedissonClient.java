package com.code.research.springboot.profile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface RedissonClient {

    <K, V> RMap<K, V> getMap(String name);

    void shutdown();

    default void close() {
        // no-op for example
    }

    interface RMap<K, V> {
        V get(K key);
        V put(K key, V value);
    }
}
