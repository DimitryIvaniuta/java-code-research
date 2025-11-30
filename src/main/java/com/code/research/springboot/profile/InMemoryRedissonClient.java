package com.code.research.springboot.profile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRedissonClient implements RedissonClient {

    private final Map<String, Map<Object, Object>> maps = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> RMap<K, V> getMap(String name) {
        Map<Object, Object> store =
                maps.computeIfAbsent(name, k -> new ConcurrentHashMap<>());

        return new RMap<>() {
            @Override
            @SuppressWarnings("unchecked")
            public V get(K key) {
                return (V) store.get(key);
            }

            @Override
            @SuppressWarnings("unchecked")
            public V put(K key, V value) {
                return (V) store.put(key, value);
            }
        };
    }

    @Override
    public void shutdown() {
        maps.clear();
    }
}
