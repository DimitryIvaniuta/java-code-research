package com.code.research.patterns.scenario.ordersaga;

import static com.code.research.patterns.scenario.ordersaga.SagaActions.*;

public class CardPaymentScenario implements OrderedScenario<OrderSagaContext, SagaStep> {

    @Override public String name() { return "Card payment authorize"; }
    @Override public int order() { return 10; }

    @Override
    public boolean matches(OrderSagaContext ctx) {
        return "CARD".equalsIgnoreCase(ctx.paymentMethod());
    }

    @Override
    public SagaStep execute(OrderSagaContext ctx) {
        // Authorize on payment service; cancel authorization on compensation.
        return SagaStep.step(PAYMENT_AUTHORIZE, PAYMENT_CANCEL);
    }
}
