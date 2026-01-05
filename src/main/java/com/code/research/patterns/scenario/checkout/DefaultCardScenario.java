package com.code.research.patterns.scenario.checkout;

import java.math.BigDecimal;

final class DefaultCardScenario implements ScenarioCheckout<CheckoutContext, CheckoutDecision> {

    @Override
    public String name() {
        return "DEFAULT CARD";
    }

    @Override
    public boolean matches(CheckoutContext ctx) {
        // Generic fallback for card payments (after VIP/high-amount rules)
        return "CARD".equals(ctx.paymentMethod());
    }

    @Override
    public CheckoutDecision execute(CheckoutContext ctx) {
        return new CheckoutDecision(
                "STRIPE",
                false,
                BigDecimal.ZERO,
                "STANDARD"
        );
    }
}
