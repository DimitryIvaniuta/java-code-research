package com.code.research.patterns.scenario.ordersaga;

import java.util.List;

import static com.code.research.patterns.scenario.ordersaga.SagaActions.*;

public class VipDiscountScenario implements OrderedScenario<OrderSagaContext, List<SagaStep>> {

    @Override public String name() { return "VIP discount"; }
    @Override public int order() { return 10; }

    @Override
    public boolean matches(OrderSagaContext ctx) {
        return ctx.isVip();
    }

    @Override
    public List<SagaStep> execute(OrderSagaContext ctx) {
        // Apply discount before payment; revert on compensation
        return List.of(SagaStep.step(DISCOUNT_APPLY, DISCOUNT_REVERT));
    }
}
