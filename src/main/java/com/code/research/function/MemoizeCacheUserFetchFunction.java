package com.code.research.function;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Slf4j
public class MemoizeCacheUserFetchFunction {

    public record Profile(Long userId, String name, Instant loadedAt) {}

    /**
     * Memoize a pure function: caches results per key.
     * - Thread-safe via ConcurrentHashMap#computeIfAbsent (atomic per key).
     * - Disallows null results (clear failure instead of silent cache misses).
     */
    private static <T, R> Function<T, R> memoize(Function<? super T, ? extends R> fn) {
        // 1) Validate the function reference early.
        Objects.requireNonNull(fn, "fn must not be null");

        // 2) Thread-safe cache for computed values.
        final var cache = new ConcurrentHashMap<T, R>();

        // 3) Return a lambda that first consults the cache, atomically computing when absent.
        return key -> {
            // 3a) Null keys are not allowed by ConcurrentHashMap; fail fast with a clear message.
            Objects.requireNonNull(key, "memoize: key must not be null");

            // 3b) Atomically compute and insert value for the missing key.
            //     For a given key, only one thread runs the mapping function; others wait and get the same value.
            return cache.computeIfAbsent(key, k -> {
                // 3c) Execute the expensive function exactly once per cache miss.
                R value = fn.apply(k);
                // 3d) Protect against null results: ConcurrentHashMap cannot store null values.
                //     Also avoids "recompute forever" behavior when a null would otherwise return "absent".
                return Objects.requireNonNull(value, "memoized function returned null for key: " + k);
            });
        };
    }

    public static Profile fetchByUserId(String userId) {
        log.info("fetchByUserId: {}", userId);
        return new Profile(1L, userId, Instant.now());
    }

    public static void main(String[] args) {
        Function<String, Profile> cachedFetch = memoize(MemoizeCacheUserFetchFunction::fetchByUserId);
        log.info("User1 apply1: {}", cachedFetch.apply("U1"));
        log.info("User2 apply: {}", cachedFetch.apply("U2"));
        log.info("User1 apply2: {}", cachedFetch.apply("U1"));
        log.info("User1 apply3: {}", cachedFetch.apply("U1"));
    }
}
