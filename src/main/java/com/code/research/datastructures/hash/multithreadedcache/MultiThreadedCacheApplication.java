package com.code.research.datastructures.hash.multithreadedcache;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MultiThreadedCacheApplication {
    
    public static void main(String[] args) {
        PersistentStore<Integer, String> store = new ConsolePersistentStore<>();
        // Create a cache with a flush interval of 5 seconds.
        try (MultiThreadedWriteThroughCache<Integer, String> cache = new MultiThreadedWriteThroughCache<>(store, 5)) {
            // Put initial entries into the cache.
            cache.put(1, "Apple");
            cache.put(2, "Banana");
            cache.put(3, "Cherry");

            log.info("Initial cache values:");
            log.info("Key 1: {}", cache.get(1)); // Should print "Apple"
            log.info("Key 2: {}", cache.get(2)); // Should print "Banana"
            log.info("Key 3: {}", cache.get(3)); // Should print "Cherry"

            // Update an entry and add a new entry.
            cache.put(2, "Blueberry");
            cache.put(4, "Date");

            // Read key 4 from cache (or persistent store if not present).
            log.info("Key 4: {}", cache.get(4)); // Should print "Date"

            // Allow time for the background flush to run.
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } // try-with-resources ensures cache.close() is called.
    }
    
}
