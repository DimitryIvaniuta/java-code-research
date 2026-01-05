package com.code.research.patterns.scenario.checkout;

import java.math.BigDecimal;
import java.util.Objects;

public record CheckoutContext(
        String customerType,   // "VIP", "REGULAR"
        String paymentMethod,  // "CARD", "PAYPAL"
        String country,        // "PL", "DE", ...
        BigDecimal amount
) {
    public CheckoutContext {
        Objects.requireNonNull(customerType);
        Objects.requireNonNull(paymentMethod);
        Objects.requireNonNull(country);
        Objects.requireNonNull(amount);
    }
}

