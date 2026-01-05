package com.code.research.patterns.scenario.checkout;

import java.math.BigDecimal;

public record CheckoutDecision(
        String paymentGateway,     // e.g. "ADYEN", "STRIPE", "PAYPAL"
        boolean require3ds,
        BigDecimal discountPercent, // e.g. 0.05 = 5%
        String riskPolicy          // e.g. "STANDARD", "HIGH"
) {}

