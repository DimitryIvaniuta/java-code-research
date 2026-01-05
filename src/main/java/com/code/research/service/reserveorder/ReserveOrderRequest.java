package com.code.research.service.reserveorder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ReserveOrderRequest(
        @NotBlank String orderId,
        @NotNull BigDecimal amount,
        @NotBlank String currency
) {
}
