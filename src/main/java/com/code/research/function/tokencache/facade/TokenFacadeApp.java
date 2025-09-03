package com.code.research.function.tokencache.facade;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TokenFacadeApp {

    public static void main(String[] args) throws Exception {
        // Setup: 5s TTL with 1s refresh-ahead
        var generator = new HmacJwtLikeGenerator("super-secret-key-123".getBytes(StandardCharsets.UTF_8));
        var service   = new GeneratedTokenService(generator, Duration.ofSeconds(5), Duration.ofSeconds(1));
        var facade    = new AccessTokenFacade(service);

        var http      = HttpClient.newHttpClient();
        var client    = new AuthorizedHttpClient(http, facade);

        var user = new User("U-42", "dev@example.com", Set.of("USER","ADMIN"));

        // === 1) Direct token usage via Facade (no HTTP) ===
        Token t1 = facade.tokenFor(user);
        System.out.println("Token 1: " + t1.value());
        System.out.println("Minted so far: " + generator.mintedCount()); // -> 1

        // reuse within TTL
        Token t2 = facade.tokenFor(user);
        System.out.println("Same token within TTL? " + t1.value().equals(t2.value())); // true
        System.out.println("Minted so far: " + generator.mintedCount()); // -> 1

        // === 2) HTTP usage via Decorator (Authorization header auto-injected) ===
        HttpRequest req = HttpRequest.newBuilder(URI.create("https://example.org/api/ping"))
                .GET()
                .build();

        // (This will try a real network call if you let it run; for demo purposes,
        // we're focusing on header injection behavior, not the response.)
        try {
            client.sendAsync(user, req, HttpResponse.BodyHandlers.discarding())
                    .orTimeout(500, TimeUnit.MILLISECONDS)
                    .exceptionally(e -> { System.out.println("HTTP skipped: " + e.getClass().getSimpleName()); return null; })
                    .join();
        } catch (Exception ignored) {}

        // === 3) Concurrent callers coalesce ===
        var pool = Executors.newFixedThreadPool(8);
        var start = new CountDownLatch(1);
        var tasks = new ArrayList<CompletableFuture<?>>();
        for (int i = 0; i < 20; i++) {
            tasks.add(CompletableFuture.runAsync(() -> {
                try { start.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                facade.tokenFor(user); // all should reuse
            }, pool));
        }
        start.countDown();
        CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new)).join();
        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("Minted so far after concurrency: " + generator.mintedCount()); // still 1

        // === 4) Near-expiry refresh-ahead ===
        Thread.sleep(4200); // ~4.2s (TTL=5s, refreshAhead=1s)
        Token t3 = facade.tokenFor(user);
        System.out.println("Refreshed? " + !t2.value().equals(t3.value())); // true
        System.out.println("Minted so far: " + generator.mintedCount()); // -> 2

        // === 5) Evict (logout / key rotation) ===
        service.evict(user.id());
        Token t4 = facade.tokenFor(user);
        System.out.println("After evict, new token? " + !t3.value().equals(t4.value())); // true
        System.out.println("Minted so far: " + generator.mintedCount()); // -> 3
    }
}


