package com.code.research.algorithm.interviewpatterns;

import com.code.research.interviewpatterns.LRUCache;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    @Test
    void evictsLeastRecentlyUsed() {
        LRUCache<Integer, String> c = new LRUCache<>(2);

        c.put(1, "A");
        c.put(2, "B");
        // Access 1 => now 2 is LRU
        assertEquals("A", c.get(1));

        c.put(3, "C"); // should evict key=2

        assertNotNull(c.get(1));
        assertNull(c.get(2));
        assertNotNull(c.get(3));
        assertEquals("[1, 3]", c.keysInOrder()); // LRU -> MRU
    }

    @Test
    void putExistingKeyMovesToMostRecent() {
        LRUCache<Integer, String> c = new LRUCache<>(2);

        c.put(1, "A");
        c.put(2, "B");

        // Update key=1 => makes it MRU
        c.put(1, "A2");

        // Now 2 is LRU, so adding 3 should evict 2
        c.put(3, "C");

        assertEquals("A2", c.get(1));
        assertNull(c.get(2));
        assertEquals("C", c.get(3));
    }

    @Test
    void basicThreadSafetySmokeTest() throws Exception {
        LRUCache<Integer, Integer> c = new LRUCache<>(50);

        int threads = 8;
        int opsPerThread = 10_000;

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<?>> futures = new ArrayList<>();
        AtomicBoolean failed = new AtomicBoolean(false);

        for (int t = 0; t < threads; t++) {
            int base = t * 1_000_000;
            futures.add(pool.submit(() -> {
                try {
                    start.await();
                    for (int i = 0; i < opsPerThread; i++) {
                        int key = (base + i) % 200; // force contention + eviction
                        c.put(key, i);
                        c.get(key);
                    }
                } catch (Throwable e) {
                    failed.set(true);
                }
            }));
        }

        start.countDown();
        for (Future<?> f : futures) f.get(10, TimeUnit.SECONDS);

        pool.shutdownNow();

        assertFalse(failed.get(), "No thread should fail");
        assertTrue(c.size() <= 50, "Size must never exceed capacity");
    }
}
