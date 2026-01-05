package com.code.research.patterns.scenario.ordersaga;

import static com.code.research.patterns.scenario.ordersaga.SagaActions.*;

public class PickupShippingScenario implements OrderedScenario<OrderSagaContext, SagaStep> {

    @Override public String name() { return "Pickup shipping reserve"; }
    @Override public int order() { return 20; }

    @Override
    public boolean matches(OrderSagaContext ctx) {
        return "PICKUP".equalsIgnoreCase(ctx.shippingMethod());
    }

    @Override
    public SagaStep execute(OrderSagaContext ctx) {
        return SagaStep.step(SHIPPING_RESERVE, SHIPPING_RELEASE);
    }
}
