package com.code.research.service.reserveorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final OrderRepository orderRepository;          // ReactiveCrudRepository<OrderEntity, String>
    private final DatabaseClient db;                        // from spring-boot-starter-data-r2dbc
    private final ReactiveTransactionManager txManager;     // R2dbcTransactionManager
    private final ObjectMapper objectMapper;                // Spring Boot provides it automatically

    /**
     * Reserve order with idempotency.
     * - First request with Idempotency-Key => does work and stores response in idempotency table.
     * - Retry with same key => returns stored response.
     * - If another request is currently processing same key => throws RequestInProgressException.
     */
    public Mono<ReserveOrderResponse> reserve(ReserveOrderRequest req, String idempotencyKey) {
        Objects.requireNonNull(req, "req must not be null");
        Objects.requireNonNull(idempotencyKey, "idempotencyKey must not be null");

        var tx = TransactionalOperator.create(txManager);

        return claimIdempotencyKey(idempotencyKey)
                .flatMap(claim -> {
                    if (claim == ClaimResult.ALREADY_COMPLETED) {
                        return loadCachedResponse(idempotencyKey);
                    }
                    if (claim == ClaimResult.IN_PROGRESS) {
                        return Mono.error(new RequestInProgressException(idempotencyKey));
                    }

                    // NEW claim => do the reservation inside a reactive transaction
                    return tx.transactional(doReserve(req))
                            .flatMap(resp -> saveCachedResponse(idempotencyKey, 200, resp).thenReturn(resp))
                            .onErrorResume(ex ->
                                    // Mark as FAILED to avoid "forever IN_PROGRESS"
                                    markFailed(idempotencyKey, ex).then(Mono.error(ex))
                            );
                });
    }

    /**
     * Reservation logic. Keep it pure + transactional.
     * You can replace this with the "atomic update" CTE approach if you prefer.
     */
    private Mono<ReserveOrderResponse> doReserve(ReserveOrderRequest req) {
        return orderRepository.findById(req.orderId())
                .switchIfEmpty(Mono.error(new OrderNotFoundException(req.orderId())))
                .flatMap(order -> {
                    // Simple state transition (extend as needed)
                    if ("RESERVED".equals(order.getStatus())) {
                        return Mono.just(new ReserveOrderResponse(order.getId(), "RESERVED", order.getReservedUntil()));
                    }
                    if (!"NEW".equals(order.getStatus())) {
                        return Mono.error(new IllegalStateException("Cannot reserve order in status=" + order.getStatus()));
                    }

                    order.setStatus("RESERVED");
                    order.setReservedUntil(Instant.now().plusSeconds(600)); // 10 min TTL example

                    return orderRepository.save(order)
                            .map(saved -> new ReserveOrderResponse(saved.getId(), "RESERVED", saved.getReservedUntil()));
                });
    }

    // -------------------------
    // Idempotency table methods
    // -------------------------

    /**
     * Try to insert Idempotency-Key as IN_PROGRESS.
     * - success => NEWLY_CLAIMED
     * - duplicate => check if completed or still in progress
     */
    private Mono<ClaimResult> claimIdempotencyKey(String key) {
        String insertSql = """
                INSERT INTO idempotency (key, status, created_at, updated_at)
                VALUES (:key, 'IN_PROGRESS', now(), now())
                """;

        return db.sql(insertSql)
                .bind("key", key) // <<< matches :key
                .fetch()
                .rowsUpdated()
                .map(rows -> rows > 0 ? ClaimResult.NEWLY_CLAIMED : ClaimResult.IN_PROGRESS)
                .onErrorResume(DuplicateKeyException.class, ex ->
                        // key already exists => see if it's completed or still running
                        loadIdempotencyMeta(key).map(meta ->
                                "COMPLETED".equals(meta.status()) ? ClaimResult.ALREADY_COMPLETED : ClaimResult.IN_PROGRESS
                        )
                );
    }

    private Mono<IdemMeta> loadIdempotencyMeta(String key) {
        String sql = """
                SELECT status, http_status
                FROM idempotency
                WHERE key = :key
                """;

        return db.sql(sql)
                .bind("key", key)
                .map((row, meta) -> new IdemMeta(
                        row.get("status", String.class),
                        row.get("http_status", Integer.class)
                ))
                .one();
    }

    private Mono<ReserveOrderResponse> loadCachedResponse(String key) {
        String sql = """
                SELECT response_json
                FROM idempotency
                WHERE key = :key
                """;

        return db.sql(sql)
                .bind("key", key)
                .map((row, meta) -> row.get("response_json", String.class))
                .one()
                .flatMap(json -> {
                    if (json == null || json.isBlank()) {
                        // Completed without body is a bug; treat as in-progress to be safe
                        return Mono.error(new RequestInProgressException(key));
                    }
                    return fromJson(json, ReserveOrderResponse.class);
                });
    }

    private Mono<Void> saveCachedResponse(String key, int httpStatus, ReserveOrderResponse response) {
        return toJson(response)
                .flatMap(json -> db.sql("""
                                UPDATE idempotency
                                SET status = 'COMPLETED',
                                    http_status = :httpStatus,
                                    response_json = :json,
                                    updated_at = now()
                                WHERE key = :key
                                """)
                        .bind("key", key)
                        .bind("httpStatus", httpStatus)
                        .bind("json", json)
                        .fetch()
                        .rowsUpdated()
                        .then()
                );
    }

    private Mono<Void> markFailed(String key, Throwable ex) {
        String msg = ex.getClass().getSimpleName();

        return db.sql("""
                        UPDATE idempotency
                        SET status = 'FAILED',
                            error = :err,
                            updated_at = now()
                        WHERE key = :key
                        """)
                .bind("key", key)
                .bind("err", msg)
                .fetch()
                .rowsUpdated()
                .then();
    }

    // -------------------------
    // JSON helpers (real mapper)
    // -------------------------

    private <T> Mono<String> toJson(T value) {
        // Jackson is CPU work; push off event-loop to boundedElastic
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(value))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private <T> Mono<T> fromJson(String json, Class<T> type) {
        return Mono.fromCallable(() -> objectMapper.readValue(json, type))
                .subscribeOn(Schedulers.boundedElastic());
    }

    // -------------------------
    // Small internal types
    // -------------------------

    private enum ClaimResult {
        NEWLY_CLAIMED,
        IN_PROGRESS,
        ALREADY_COMPLETED
    }

    private record IdemMeta(String status, Integer httpStatus) {
    }

    // -------------------------
    // DTOs (example shape)
    // -------------------------

    public record ReserveOrderRequest(String orderId) {
    }

    public record ReserveOrderResponse(String orderId, String status, Instant reservedUntil) {
    }

    // -------------------------
    // Exceptions (map to 404 / 409 in controller advice)
    // -------------------------

    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String orderId) {
            super("Order not found: " + orderId);
        }
    }

    public static class RequestInProgressException extends RuntimeException {
        public RequestInProgressException(String key) {
            super("Request with this Idempotency-Key is in progress: " + key);
        }
    }
}
