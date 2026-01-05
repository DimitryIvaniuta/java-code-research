package com.code.research.patterns.scenario.checkout;

import java.math.BigDecimal;
import java.util.List;

public class ScenarioCheckoutDemo {
    public static void main(String[] args) {
        // Ordering matters: most specific first, generic last.
        var engine = new ScenarioCheckoutEngine<CheckoutContext, CheckoutDecision>(
                List.of(
                        new HighAmountRiskScenario(),
                        new VipCardEuScenario(),
                        new PaypalScenario(),
                        new DefaultCardScenario()
                )
        );

        var ctx = new CheckoutContext("VIP", "CARD", "PL", new BigDecimal("1200"));
        var decision = engine.execute(ctx);

        System.out.println("Decision: " + decision);
    }
}