package com.code.research.springboot.payload;

// JSON:
// {
//   "customerId": "123",
//   "total": 99.90,
//   "items": [
//     { "sku": "SKU-1", "quantity": 2 },
//     { "sku": "SKU-2", "quantity": 1 }
//   ]
// }
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        @NotBlank String customerId,
        @NotEmpty List<@Valid OrderItemRequest> items,
        BigDecimal total
) {}
