package com.code.research.service.reservecte;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.OffsetDateTime;

@Repository
public class OrderReserveRepositoryV2 {

    private final DatabaseClient db;

    public OrderReserveRepositoryV2(DatabaseClient db) {
        this.db = db;
    }

    /**
     * Single CTE query (returns 200 / 404 / 409 in one round-trip). Uses WebFlux + R2DBC DatabaseClient.
     * Atomic reservation using ONE SQL statement:
     * - If order is NEW OR expired OR already reserved by same user => UPDATE succeeds => 200
     * - If order exists but reserved by another user and not expired => 409
     * - If order does not exist => 404
     */
    public Mono<ReserveDbRow> reserveAtomically(String orderId, String userId, int ttlSeconds) {
        return db.sql(SQL)
                .bind("orderId", orderId)
                .bind("userId", userId)
                .bind("ttlSeconds", ttlSeconds)
                .map((row, meta) -> new ReserveDbRow(
                        row.get("http_status", Integer.class),
                        row.get("order_id", String.class),
                        row.get("reserved_by", String.class),
                        toInstant(row.get("reserved_until", OffsetDateTime.class)),
                        row.get("current_status", String.class)
                ))
                .one(); // query always returns exactly 1 row
    }

    private static Instant toInstant(OffsetDateTime odt) {
        return odt == null ? null : odt.toInstant();
    }

    /**
     * Enhanced single-row output:
     * - For 200: returns fresh reserved_by/reserved_until
     * - For 409: returns current reserved_by/reserved_until (who holds reservation and until when)
     * - For 404: returns nulls
     */
    private static final String SQL = """
            WITH try_reserve AS (
              UPDATE orders
              SET status         = 'RESERVED',
                  reserved_by    = :userId,
                  reserved_until = now() + (:ttlSeconds || ' seconds')::interval,
                  updated_at     = now()
              WHERE id = :orderId
                AND status IN ('NEW', 'RESERVED')
                AND (
                     status = 'NEW'
                  OR reserved_until < now()
                  OR reserved_by = :userId
                )
              RETURNING id, reserved_by, reserved_until
            ),
            current_order AS (
              SELECT id, status, reserved_by, reserved_until
              FROM orders
              WHERE id = :orderId
            )
            SELECT
              CASE
                WHEN EXISTS (SELECT 1 FROM try_reserve) THEN 200
                WHEN EXISTS (SELECT 1 FROM current_order) THEN 409
                ELSE 404
              END AS http_status,
            
              COALESCE(
                (SELECT id FROM try_reserve),
                (SELECT id FROM current_order),
                :orderId
              ) AS order_id,
            
              COALESCE(
                (SELECT reserved_by FROM try_reserve),
                (SELECT reserved_by FROM current_order)
              ) AS reserved_by,
            
              COALESCE(
                (SELECT reserved_until FROM try_reserve),
                (SELECT reserved_until FROM current_order)
              ) AS reserved_until,
            
              (SELECT status FROM current_order) AS current_status
            ;
            """;
}
