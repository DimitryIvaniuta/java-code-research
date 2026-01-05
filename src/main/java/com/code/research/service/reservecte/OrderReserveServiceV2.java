package com.code.research.service.reservecte;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderReserveServiceV2 {

    private final OrderReserveRepositoryV2 repo;

    public OrderReserveServiceV2(OrderReserveRepositoryV2 repo) {
        this.repo = repo;
    }

    public Mono<ResponseEntity<ReserveOrderResponse>> reserve(String orderId, String userId) {
        int ttlSeconds = 600; // 10 minutes

        return repo.reserveAtomically(orderId, userId, ttlSeconds)
                .map(row -> switch (row.httpStatus()) {
                    case 200 -> ResponseEntity.ok(new ReserveOrderResponse(
                            row.orderId(), "RESERVED", row.reservedBy(), row.reservedUntil()
                    ));
                    case 409 -> ResponseEntity.status(HttpStatus.CONFLICT).body(new ReserveOrderResponse(
                            row.orderId(), "ALREADY_RESERVED", row.reservedBy(), row.reservedUntil()
                    ));
                    case 404 -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReserveOrderResponse(
                            orderId, "NOT_FOUND", null, null
                    ));
                    default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ReserveOrderResponse(
                            orderId, "ERROR", null, null
                    ));
                });
    }
}
