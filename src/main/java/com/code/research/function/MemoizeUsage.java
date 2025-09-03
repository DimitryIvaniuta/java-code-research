package com.code.research.function;

import java.time.Instant;
import java.util.Objects;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class MemoizeUsage {

    // ----- The memoize helper (your snippet) -----
    public static <T, R> Function<T, R> memoize(Function<T, R> fn) {
        var cache = new ConcurrentHashMap<T, R>();
        return key -> cache.computeIfAbsent(key, fn); // mapping function called at most once per key
    }

    public record Profile(String userId, String name, Instant loadedAt) {}

    static final class ProfileService {
        private final AtomicInteger calls = new AtomicInteger();

        // Simulate an expensive remote call (DB/HTTP) with latency
        public Profile fetchByUserId(String userId) {
            int n = calls.incrementAndGet();
            simulateLatency(150); // pretend we're hitting DB/external service
            System.out.printf("FETCH #%d from source for userId=%s%n", n, userId);
            return new Profile(userId, "User-" + userId, Instant.now());
        }

        public int invocations() { return calls.get(); }

        private static void simulateLatency(long millis) {
            try { Thread.sleep(millis); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    public static void main(String[] args) throws Exception {
        var svc = new ProfileService();

        // Wrap the expensive fetch in a memoized Function
        Function<String, Profile> cachedFetch = memoize(svc::fetchByUserId);

        System.out.println("=== Sequential calls (same keys hit cache) ===");
        List<String> ids = List.of("U1", "U1", "U2", "U1", "U3", "U2");
        for (String id : ids) {
            Profile p = cachedFetch.apply(id); // only the first time per key triggers a real fetch
            System.out.printf("Got profile %s at %s%n", p.name(), p.loadedAt());
        }
        System.out.printf("Expensive fetches performed: %d (should equal unique keys: 3)%n%n", svc.invocations());

        System.out.println("=== Concurrent calls (many threads, same key) ===");
        // Prove computeIfAbsent runs the mapping function once per key even under contention
        ExecutorService pool = Executors.newFixedThreadPool(8);
        String hotKey = "U-HOT";
        int callers = 20;
        var futures = new CompletableFuture[callers];
        for (int i = 0; i < callers; i++) {
            futures[i] = CompletableFuture.runAsync(() -> {
                Profile p = cachedFetch.apply(hotKey); // concurrent callers race on the same key
                if (!Objects.equals(p.userId(), hotKey)) throw new AssertionError("Wrong profile");
            }, pool);
        }
        CompletableFuture.allOf(futures).join();
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.printf("Total expensive fetches performed: %d (should be 4 now: U1,U2,U3,U-HOT)%n", svc.invocations());

        System.out.println("\n=== Bonus: reusing cached values ===");
        // Show that subsequent calls are instant (no latency/extra fetch)
        long startNs = System.nanoTime();
        Profile again = cachedFetch.apply("U1");
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        System.out.printf("Cached U1 fetch took ~%d ms (near-zero), profile=%s%n", tookMs, again);
    }

}
