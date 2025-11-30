package com.code.research.springboot.profile;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class RedisCacheManager implements CacheManager {

    private final RedissonClient redissonClient;
    private final Map<String, Cache> caches = new ConcurrentHashMap<>();

    public RedisCacheManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Cache getCache(String name) {
        return caches.computeIfAbsent(name,
                n -> new RedisCache(n, redissonClient.getMap("cache:" + n)));
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(caches.keySet());
    }

    private static class RedisCache implements Cache {

        private final String name;
        private final RedissonClient.RMap<Object, Object> store;

        RedisCache(String name, RedissonClient.RMap<Object, Object> store) {
            this.name = name;
            this.store = store;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Object getNativeCache() {
            return store;
        }

        @Override
        public ValueWrapper get(Object key) {
            Object value = store.get(key);
            return (value != null ? new SimpleValueWrapper(value) : null);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T get(Object key, Class<T> type) {
            Object value = store.get(key);
            if (value == null) {
                return null;
            }
            if (type != null && !type.isInstance(value)) {
                throw new IllegalStateException("Cached value is not of required type " + type);
            }
            return (T) value;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T get(Object key, Callable<T> valueLoader) {
            Object value = store.get(key);
            if (value != null) {
                return (T) value;
            }
            try {
                T loaded = valueLoader.call();
                store.put(key, loaded);
                return loaded;
            } catch (Exception ex) {
                throw new ValueRetrievalException(key, valueLoader, ex);
            }
        }

        @Override
        public void put(Object key, Object value) {
            store.put(key, value);
        }

        @Override
        @SuppressWarnings("unchecked")
        public ValueWrapper putIfAbsent(Object key, Object value) {
            Object existing = store.get(key);
            if (existing == null) {
                store.put(key, value);
                return null;
            }
            return new SimpleValueWrapper(existing);
        }

        @Override
        public void evict(Object key) {
            store.put(key, null); // or store.remove(key) if your impl supports it
        }

        @Override
        public void clear() {
            // for real Redisson: store.clear();
            // for our simple RMap, emulate:
            store.put("__clear__", System.nanoTime()); // no-op for stub, adapt if needed
        }
    }
}
