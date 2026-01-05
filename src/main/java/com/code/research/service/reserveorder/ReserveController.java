package com.code.research.service.reserveorder;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class ReserveController {

    private final ReserveService service;

    public ReserveController(ReserveService service) {
        this.service = service;
    }

    @PostMapping("/reserve")
    public Mono<ResponseEntity<ReserveOrderResponse>> reserve(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody ReserveOrderRequest req
    ) {
        return service.reserve(req, idempotencyKey)
                .map(ResponseEntity::ok);
    }
}
