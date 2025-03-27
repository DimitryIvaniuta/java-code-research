package com.code.research.datastructures.hash.lfucache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LFUCacheImplApplication {

    /**
     * Main method demonstrating the usage of LFUCache.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        LFUCacheImpl<Integer, String> cache = new LFUCacheImpl<>(3);

        // Insert initial entries.
        cache.put(1, "Apple");
        cache.put(2, "Banana");
        cache.put(3, "Cherry");

        log.info("Get key 1: {}", cache.get(1)); // Expected "Apple" (increases frequency of key 1)

        // This put should evict the key with lowest frequency.
        cache.put(4, "Date");

        // Check the state of the cache.
        log.info("Get key 2 (expected eviction): {}", cache.get(2)); // Expected null if key 2 was evicted.
        log.info("Get key 3: {}", cache.get(3));  // Expected "Cherry"
        log.info("Get key 4: {}", cache.get(4));  // Expected "Date"

        // Demonstrate updating an existing key.
        cache.put(3, "Cherry Updated");
        log.info("Get updated key 3: {}", cache.get(3)); // Expected "Cherry Updated"
    }
}
