package com.code.research.patterns.scenario.ordersaga;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record OrderSagaContext(
        String correlationId,
        String orderId,
        String customerType,     // VIP, REGULAR
        String paymentMethod,    // CARD, PAYPAL, COD
        String shippingMethod,   // COURIER, PICKUP, NONE
        String country,          // PL, DE, ...
        BigDecimal totalAmount,
        Set<String> couponCodes,
        List<OrderItem> items
) {
    public boolean hasCoupons() {
        return couponCodes != null && !couponCodes.isEmpty();
    }

    public boolean hasDigitalOnly() {
        return items != null && !items.isEmpty() && items.stream().allMatch(OrderItem::digital);
    }

    public boolean isVip() {
        return "VIP".equalsIgnoreCase(customerType);
    }

    public boolean isHighValue() {
        return totalAmount != null && totalAmount.compareTo(new BigDecimal("5000")) >= 0;
    }
}
