package com.code.research.patterns.scenario.checkout;

import java.math.BigDecimal;
import java.util.Set;

final class VipCardEuScenario implements ScenarioCheckout<CheckoutContext, CheckoutDecision> {

    private static final Set<String> EU = Set.of("PL", "DE", "FR", "IT", "ES", "NL");

    @Override
    public String name() {
        return "VIP + CARD + EU";
    }

    @Override
    public boolean matches(CheckoutContext ctx) {
        // Scenario condition: VIP customer, pays by card, and is in EU
        return "VIP".equals(ctx.customerType())
                && "CARD".equals(ctx.paymentMethod())
                && EU.contains(ctx.country());
    }

    @Override
    public CheckoutDecision execute(CheckoutContext ctx) {
        // Scenario logic:
        // - Use ADYEN for EU card payments
        // - Require 3DS for compliance
        // - Give VIP discount
        return new CheckoutDecision(
                "ADYEN",
                true,
                new BigDecimal("0.05"),     // 5% discount
                "STANDARD"
        );
    }
}




