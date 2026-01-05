package com.code.research.service.reserve;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReserveService {

    private final OrderReserveRepository repo;
    private final DatabaseClient db;

    public ReserveService(OrderReserveRepository repo, DatabaseClient db) {
        this.repo = repo;
        this.db = db;
    }

    public Mono<ReserveOrderResponse> reserve(String orderId, String userId) {
        int ttlSeconds = 600;

        return repo.tryReserve(orderId, userId, ttlSeconds)
                .map(r -> new ReserveOrderResponse(r.orderId(), "RESERVED", r.reservedUntil()))
                .switchIfEmpty(checkExistsThenConflict(orderId));
    }

    private Mono<ReserveOrderResponse> checkExistsThenConflict(String orderId) {
        return db.sql("select id from orders where id = :id")
                .bind("id", orderId)
                .map((row, meta) -> row.get("id", String.class))
                .one()
                .map(x -> new ReserveOrderResponse(orderId, "ALREADY_RESERVED", null))
                .switchIfEmpty(Mono.error(new Exception("Order not found")));
    }
}
