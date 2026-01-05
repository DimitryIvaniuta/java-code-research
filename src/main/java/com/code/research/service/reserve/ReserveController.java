package com.code.research.service.reserve;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Reserve with a single conditional UPDATE in Postgres.
 * It updates the row only if it’s free or expired, and returns the row via RETURNING.
 * If the update affects zero rows, I return 409.
 * This avoids race conditions without explicit locks because the database enforces atomicit
 */
@RestController
@AllArgsConstructor
public class ReserveController {

    private ReserveService service;

    @PostMapping("/orders/{orderId}/reserve")
    public Mono<ResponseEntity<ReserveOrderResponse>> reserve(
            @PathVariable String orderId,
            @AuthenticationPrincipal(expression = "name") String userId
    ) {
        return service.reserve(orderId, userId)
                .map(resp -> "RESERVED".equals(resp.status())
                        ? ResponseEntity.ok(resp)
                        : ResponseEntity.status(409).body(resp));
    }


}
