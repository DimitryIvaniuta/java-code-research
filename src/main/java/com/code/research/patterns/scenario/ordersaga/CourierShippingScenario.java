package com.code.research.patterns.scenario.ordersaga;

import static com.code.research.patterns.scenario.ordersaga.SagaActions.*;

public class CourierShippingScenario implements OrderedScenario<OrderSagaContext, SagaStep> {

    @Override public String name() { return "Courier shipping reserve"; }
    @Override public int order() { return 10; }

    @Override
    public boolean matches(OrderSagaContext ctx) {
        return "COURIER".equalsIgnoreCase(ctx.shippingMethod());
    }

    @Override
    public SagaStep execute(OrderSagaContext ctx) {
        return SagaStep.step(SHIPPING_RESERVE, SHIPPING_RELEASE);
    }
}
