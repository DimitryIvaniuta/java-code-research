package com.code.research.patterns.scenario.ordersaga;

import java.util.List;

import static com.code.research.patterns.scenario.ordersaga.SagaActions.*;

public class CouponDiscountScenario implements OrderedScenario<OrderSagaContext, List<SagaStep>> {

    @Override public String name() { return "Coupon discount"; }
    @Override public int order() { return 20; }

    @Override
    public boolean matches(OrderSagaContext ctx) {
        return ctx.hasCoupons();
    }

    @Override
    public List<SagaStep> execute(OrderSagaContext ctx) {
        return List.of(SagaStep.step(DISCOUNT_APPLY, DISCOUNT_REVERT));
    }
}
