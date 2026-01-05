package com.code.research.service.reserve;

public record ReservationResult(String orderId, String reservedBy, java.time.Instant reservedUntil) {
}