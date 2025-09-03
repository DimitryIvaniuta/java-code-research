package com.code.research.function;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Slf4j
public class MemoizeCacheUserFetchFunction {

    public record Profile(String name, Instant loadedAt) {}

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

    /**
     * Memoize and allow null results by caching Optional.
     */
    public static <T, R> Function<T, R> memoizeNullable(Function<? super T, ? extends R> fn) {
        final ConcurrentHashMap<T, Optional<R>> cache = new ConcurrentHashMap<>();
        return key -> cache.computeIfAbsent(
                key,
                k -> Optional.ofNullable(fn.apply(k))   // cache present/empty explicitly
        ).orElse(null);                              // unwrap to the caller's expected null
    }


    /**
     * Memoize with in-flight coalescing and no failure caching.
     * - Concurrent callers for the same key wait on the same CompletableFuture.
     * - If computation fails, the failed future is removed so a later call can retry.
     */
    public static <T, R> Function<T, R> memoizeCoalescing(Function<? super T, ? extends R> fn) {
        Objects.requireNonNull(fn, "fn must not be null");
        final ConcurrentHashMap<T, CompletableFuture<R>> cache = new ConcurrentHashMap<>();

        return key -> {
            Objects.requireNonNull(key, "memoize: key must not be null");

            // 1) Get or install the "in-flight" future atomically.
            final CompletableFuture<R> future = cache.computeIfAbsent(key, k -> {
                // Compute synchronously and complete the future before returning it.
                final CompletableFuture<R> created = new CompletableFuture<>();
                try {
                    R value = fn.apply(k);
                    if (value == null) throw new NullPointerException("memoized function returned null for key: " + k);
                    created.complete(value);
                } catch (Throwable t) {
                    created.completeExceptionally(t);
                }
                return created;
            });

            try {
                // 2) All callers join the same result.
                return future.join();
            } catch (CompletionException ce) {
                // 3) On failure, evict the failed future so subsequent calls can retry.
                cache.remove(key, future);
                throw ce;
            }
        };
    }

    public static Profile fetchByUserId(String userId) {
        log.info("fetchByUserId: {}", userId);
        return new Profile(userId, Instant.now());
    }

    public static void main(String[] args) {
        Function<String, Profile> cachedFetch = memoize(MemoizeCacheUserFetchFunction::fetchByUserId);
        log.info("User1 apply1: {}", cachedFetch.apply("U1"));
        log.info("User2 apply: {}", cachedFetch.apply("U2"));
        log.info("User1 apply2: {}", cachedFetch.apply("U1"));
        log.info("User1 apply3: {}", cachedFetch.apply("U1"));

        Function<String, Profile> cachedFetchOptional = memoizeNullable(MemoizeCacheUserFetchFunction::fetchByUserId);

        log.info("User3 apply1 optional: {}", cachedFetchOptional.apply("U3"));
        log.info("User4 apply optional: {}", cachedFetchOptional.apply("U4"));
        log.info("User3 apply2 optional: {}", cachedFetchOptional.apply("U3"));
        log.info("User3 apply3 optional: {}", cachedFetchOptional.apply("U3"));

        Function<String, Profile> cachedFetchCoalescing = memoizeCoalescing(MemoizeCacheUserFetchFunction::fetchByUserId);

        log.info("User5 apply1 optional: {}", cachedFetchCoalescing.apply("U5"));
        log.info("User6 apply optional: {}", cachedFetchCoalescing.apply("U6"));
        log.info("User5 apply2 optional: {}", cachedFetchCoalescing.apply("U5"));
        log.info("User5 apply3 optional: {}", cachedFetchCoalescing.apply("U5"));

    }
}
