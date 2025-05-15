package com.code.research.threads.completablefuture;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record User(long id, String name) {}
record Order(long id, long userId, double amount) {}
record Recommendation(long productId, String suggestion) {}
record AccountSummary(User user, List<Order> orders) {}

public class AsyncService {
    private final ExecutorService executor;

    public AsyncService(ExecutorService executor) {
        this.executor = executor;
    }

    public CompletableFuture<User> getUser(long userId) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(300); // e.g. remote DB call
            return new User(userId, "User" + userId);
        }, executor);
    }

    public CompletableFuture<List<Order>> getRecentOrders(long userId) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(500); // e.g. REST call
            // return 5 dummy orders
            return IntStream.range(1,6)
                    .mapToObj(i -> new Order(i, userId, i * 20.0))
                    .collect(Collectors.toList());
        }, executor);
    }

    public CompletableFuture<List<Recommendation>> getRecommendations(User user) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(400); // e.g. ML service
            // return 3 dummy recs
            return IntStream.range(1,4)
                    .mapToObj(i -> new Recommendation(i, "TryProduct" + i))
                    .collect(Collectors.toList());
        }, executor);
    }

    private static void simulateDelay(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
    }
}
