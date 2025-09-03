package com.code.research.function.tokencache;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
public class TokenCacheApp {

    public static void main(String[] args) throws Exception {
        // 1) Configure generator + cache policy (TTL 5s, refresh 1s before expiry)
        var generator = new HmacJwtLikeGenerator("super-secret-key-123".getBytes(StandardCharsets.UTF_8));
        var service   = new GeneratedTokenService(generator, Duration.ofSeconds(5), Duration.ofSeconds(1));
        Function<User, Token> tokenFn = service.tokenFunction();

        var u = new User("U-42", "dev@example.com", Set.of("USER", "ADMIN"));

        log.info("=== First call (miss → generate) ===");
        Token t1 = tokenFn.apply(u);
        log.info("token1: " + t1);
        log.info("minted so far: " + generator.mintedCount()); // → 1

        log.info("\n=== Reuse within TTL (hit) ===");
        Token t2 = tokenFn.apply(u);
        log.info("token2: " + t2);
        log.info("same instance/value? " + t1.value().equals(t2.value()));
        log.info("minted so far: " + generator.mintedCount()); // still 1

        log.info("\n=== Concurrent callers (coalesce to one mint) ===");
        var pool = Executors.newFixedThreadPool(8);
        var latch = new CountDownLatch(1);
        var futures = new CompletableFuture<?>[20];
        for (int i = 0; i < futures.length; i++) {
            futures[i] = CompletableFuture.runAsync(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                Token t = tokenFn.apply(u);
                if (!t.value().equals(t2.value())) {
                    // Within TTL we expect same token
                    throw new AssertionError("Unexpected token change under TTL");
                }
            }, pool);
        }
        latch.countDown();
        CompletableFuture.allOf(futures).join();
        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);
        log.info("minted so far: " + generator.mintedCount()); // still 1

        log.info("\n=== Wait until near-expiry to trigger refresh-ahead ===");
        // Sleep to enter the last ~1s (refreshAhead window) so next call regenerates.
        Thread.sleep(4200); // ~4.2s (TTL=5s, refreshAhead=1s)
        Token t3 = tokenFn.apply(u);
        log.info("token3 (refreshed): " + t3);
        log.info("token changed? " + !t2.value().equals(t3.value()));
        log.info("minted so far: " + generator.mintedCount()); // → 2

        log.info("\n=== Evict and force re-generate (e.g., logout) ===");
        service.evict(u.id());
        Token t4 = tokenFn.apply(u);
        log.info("token4 (after evict): " + t4);
        log.info("token changed after evict? " + !t3.value().equals(t4.value()));
        log.info("minted so far: " + generator.mintedCount()); // → 3
    }
    
}
