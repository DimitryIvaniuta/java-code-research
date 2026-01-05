package com.code.research.service.reservecte;

import java.time.Instant;

public record ReserveDbRow(
        Integer httpStatus,
        String orderId,
        String reservedBy,
        Instant reservedUntil,
        String currentStatus
) {
}