package com.code.research.service.reserve;

public record ReserveOrderResponse(String orderId, String status, java.time.Instant reservedUntil) {
}

