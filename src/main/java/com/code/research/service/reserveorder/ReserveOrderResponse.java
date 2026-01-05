package com.code.research.service.reserveorder;

import java.time.Instant;

public record ReserveOrderResponse(String orderId, String status, Instant reservedUntil) {
}