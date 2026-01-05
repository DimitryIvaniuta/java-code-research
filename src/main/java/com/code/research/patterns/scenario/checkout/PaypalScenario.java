package com.code.research.patterns.scenario.checkout;

import java.math.BigDecimal;

final class PaypalScenario implements ScenarioCheckout<CheckoutContext, CheckoutDecision> {

    @Override
    public String name() {
        return "PAYPAL";
    }

    @Override
    public boolean matches(CheckoutContext ctx) {
        // PayPal uses its own gateway flow, independent of country here
        return "PAYPAL".equals(ctx.paymentMethod());
    }

    @Override
    public CheckoutDecision execute(CheckoutContext ctx) {
        // PayPal typically doesn't require 3DS in our domain model
        return new CheckoutDecision(
                "PAYPAL",
                false,
                BigDecimal.ZERO,
                "STANDARD"
        );
    }
}