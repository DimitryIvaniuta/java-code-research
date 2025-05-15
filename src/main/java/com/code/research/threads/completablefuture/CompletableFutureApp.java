package com.code.research.threads.completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureApp {
    public static void main(String[] args) {
        // 1) Custom thread pool
        ExecutorService exec = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                new ThreadFactoryBuilder("async-pool-%d", true)
        );
        AsyncService svc = new AsyncService(exec);

        long userId = 42L;

        // 2) Kick off independent async calls
        CompletableFuture<User> userFut    = svc.getUser(userId)
                .orTimeout(1, TimeUnit.SECONDS)
                .exceptionally(ex -> { throw new CompletionException("Failed to fetch user", ex); });

        CompletableFuture<List<Order>> ordersFut = svc.getRecentOrders(userId)
                .orTimeout(2, TimeUnit.SECONDS)
                .exceptionally(ex -> List.of()); // empty orders on timeout/failure

        // 3) Combine user + orders into a summary
        CompletableFuture<AccountSummary> summaryFut =
                userFut.thenCombine(ordersFut, AccountSummary::new);

        // 4) Then, fetch recommendations based on the user
        CompletableFuture<List<Recommendation>> recsFut =
                userFut.thenCompose(user ->
                        svc.getRecommendations(user)
                                .orTimeout(1, TimeUnit.SECONDS)
                                .exceptionally(ex -> List.of()) // fall back to empty recs
                );

        // 5) When both summary and recs are ready, consume them
        CompletableFuture<Void> resultFut = summaryFut.thenAcceptBoth(recsFut,
                (summary, recs) -> {
                    System.out.println("Account Summary:");
                    System.out.println("  User:    " + summary.user());
                    System.out.println("  Orders:  " + summary.orders());
                    System.out.println("  Recs:    " + recs);
                }
        );

        // 6) Wait for all to finish
        try {
            resultFut.join();
        } finally {
            exec.shutdown();
        }
    }
}
