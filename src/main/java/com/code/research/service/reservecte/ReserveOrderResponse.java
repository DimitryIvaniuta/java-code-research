package com.code.research.service.reservecte;

public record ReserveOrderResponse(
        String orderId,
        String status,          // RESERVED / ALREADY_RESERVED / NOT_FOUND
        String reservedBy,      // for 409 you see who holds it (optional)
        java.time.Instant reservedUntil
) {
}