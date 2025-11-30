package com.code.research.springboot.payload;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        // use request.customerId(), request.total()
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // POST /orders?source=WEB
    // body: { "customerId": "123", "total": 50.0 }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestParam(defaultValue = "WEB") String source,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        // use source + request
        return ResponseEntity.ok().build();
    }
}
