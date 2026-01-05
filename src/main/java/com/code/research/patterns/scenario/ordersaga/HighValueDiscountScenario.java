package com.code.research.patterns.scenario.ordersaga;

import java.util.List;

import static com.code.research.patterns.scenario.ordersaga.SagaActions.*;

public class HighValueDiscountScenario implements OrderedScenario<OrderSagaContext, List<SagaStep>> {

    @Override public String name() { return "High-value discount"; }
    @Override public int order() { return 30; }

    @Override
    public boolean matches(OrderSagaContext ctx) {
        return ctx.isHighValue();
    }

    @Override
    public List<SagaStep> execute(OrderSagaContext ctx) {
        return List.of(SagaStep.step(DISCOUNT_APPLY, DISCOUNT_REVERT));
    }
}
