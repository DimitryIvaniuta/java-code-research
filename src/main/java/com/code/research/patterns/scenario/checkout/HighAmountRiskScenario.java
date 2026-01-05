package com.code.research.patterns.scenario.checkout;

import java.math.BigDecimal;

final class HighAmountRiskScenario implements ScenarioCheckout<CheckoutContext, CheckoutDecision> {

    @Override
    public String name() {
        return "HIGH AMOUNT RISK";
    }

    @Override
    public boolean matches(CheckoutContext ctx) {
        // Catch risky cases early: amount >= 5000 triggers stricter risk rules.
        // This scenario should be placed BEFORE more generic card scenario if you want it to override.
        return ctx.amount().compareTo(new BigDecimal("5000")) >= 0;
    }

    @Override
    public CheckoutDecision execute(CheckoutContext ctx) {
        // For large amounts:
        // - route to STRIPE (example)
        // - enforce 3DS for card payments
        // - apply HIGH risk policy
        boolean require3ds = "CARD".equals(ctx.paymentMethod());
        return new CheckoutDecision(
                "STRIPE",
                require3ds,
                BigDecimal.ZERO,
                "HIGH"
        );
    }
}