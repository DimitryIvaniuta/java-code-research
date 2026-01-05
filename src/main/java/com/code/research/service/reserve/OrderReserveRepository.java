package com.code.research.service.reserve;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class OrderReserveRepository {

    private final DatabaseClient db;

    public OrderReserveRepository(DatabaseClient db) {
        this.db = db;
    }

    public Mono<ReservationResult> tryReserve(String orderId, String userId, int ttlSeconds) {
        String sql = """
                UPDATE orders
                SET status = 'RESERVED',
                    reserved_by = :userId,
                    reserved_until = now() + (:ttlSeconds || ' seconds')::interval,
                    updated_at = now()
                WHERE id = :orderId
                  AND status IN ('NEW', 'RESERVED')
                  AND (
                       status = 'NEW'
                    OR reserved_until < now()
                    OR reserved_by = :userId
                  )
                RETURNING id, reserved_by, reserved_until
                """;

        return db.sql(sql)
                .bind("orderId", orderId)
                .bind("userId", userId)
                .bind("ttlSeconds", ttlSeconds)
                .map((row, meta) -> new ReservationResult(
                        row.get("id", String.class),
                        row.get("reserved_by", String.class),
                        row.get("reserved_until", java.time.OffsetDateTime.class).toInstant()
                ))
                .one(); // Mono.empty() if 0 rows updated
    }
}
