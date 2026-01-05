package com.code.research.service.reservecte;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.http.ResponseEntity;

/**
 * First winner → 200 OK + RESERVED + reservedUntil.
 * Others (while not expired) → 409 Conflict + ALREADY_RESERVED (+ who reserved + until).
 * Unknown order → 404 Not Found.
 */
@RestController
@RequestMapping("/orders")
public class OrderReserveControllerV2 {

    private final OrderReserveServiceV2 service;

    public OrderReserveControllerV2(OrderReserveServiceV2 service) {
        this.service = service;
    }

    /**
     * POST /orders/{orderId}/reserve
     * Security: expects Authorization: Bearer <JWT>
     */
    @PostMapping("/{orderId}/reserve")
    public Mono<ResponseEntity<ReserveOrderResponse>> reserve(
            @PathVariable String orderId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        // Using subject as userId; adjust to your claim if needed (e.g., jwt.getClaimAsString("email"))
        String userId = jwt.getSubject();
        return service.reserve(orderId, userId);
    }
}
