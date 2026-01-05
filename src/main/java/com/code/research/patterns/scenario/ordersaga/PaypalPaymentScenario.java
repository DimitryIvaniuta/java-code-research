package com.code.research.patterns.scenario.ordersaga;

import static com.code.research.patterns.scenario.ordersaga.SagaActions.*;

public class PaypalPaymentScenario implements OrderedScenario<OrderSagaContext, SagaStep> {

    @Override public String name() { return "PayPal authorize"; }
    @Override public int order() { return 20; }

    @Override
    public boolean matches(OrderSagaContext ctx) {
        return "PAYPAL".equalsIgnoreCase(ctx.paymentMethod());
    }

    @Override
    public SagaStep execute(OrderSagaContext ctx) {
        return SagaStep.step(PAYMENT_AUTHORIZE, PAYMENT_CANCEL);
    }
}
