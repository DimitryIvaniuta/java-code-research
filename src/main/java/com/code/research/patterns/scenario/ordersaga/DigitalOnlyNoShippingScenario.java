package com.code.research.patterns.scenario.ordersaga;

public class DigitalOnlyNoShippingScenario implements OrderedScenario<OrderSagaContext, SagaStep> {

    @Override public String name() { return "Digital-only: no shipping"; }
    @Override public int order() { return 5; }

    @Override
    public boolean matches(OrderSagaContext ctx) {
        return ctx.hasDigitalOnly();
    }

    @Override
    public SagaStep execute(OrderSagaContext ctx) {
        return new SagaStep("NOOP", "NOOP");
    }
}
