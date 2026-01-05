package com.code.research.patterns.scenario.ordersaga;

public class CashOnDeliveryScenario implements OrderedScenario<OrderSagaContext, SagaStep> {

    @Override public String name() { return "COD: no payment authorize"; }
    @Override public int order() { return 30; }

    @Override
    public boolean matches(OrderSagaContext ctx) {
        return "COD".equalsIgnoreCase(ctx.paymentMethod());
    }

    @Override
    public SagaStep execute(OrderSagaContext ctx) {
        // No payment step for COD. Return a NOOP step or handle by skipping.
        // We skip at builder level; so this should never be executed if we filter it out.
        return new SagaStep("NOOP", "NOOP");
    }
}
