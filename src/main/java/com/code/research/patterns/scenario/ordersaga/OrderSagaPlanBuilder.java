package com.code.research.patterns.scenario.ordersaga;

import java.util.ArrayList;
import java.util.List;

import static com.code.research.patterns.scenario.ordersaga.SagaActions.*;
import static com.code.research.patterns.scenario.ordersaga.ScenarioEngines.*;

public class OrderSagaPlanBuilder {

    // Multi-match (many discounts can apply)
    private final List<OrderedScenario<OrderSagaContext, List<SagaStep>>> discountScenarios;

    // Exclusive (one payment path)
    private final List<OrderedScenario<OrderSagaContext, SagaStep>> paymentScenarios;

    // Exclusive (one shipping path)
    private final List<OrderedScenario<OrderSagaContext, SagaStep>> shippingScenarios;

    public OrderSagaPlanBuilder(
            List<OrderedScenario<OrderSagaContext, List<SagaStep>>> discountScenarios,
            List<OrderedScenario<OrderSagaContext, SagaStep>> paymentScenarios,
            List<OrderedScenario<OrderSagaContext, SagaStep>> shippingScenarios
    ) {
        this.discountScenarios = List.copyOf(discountScenarios);
        this.paymentScenarios = List.copyOf(paymentScenarios);
        this.shippingScenarios = List.copyOf(shippingScenarios);
    }

    public SagaPlan build(OrderSagaContext ctx) {
        var steps = new ArrayList<SagaStep>();

        // 1) Always create order first
        steps.add(SagaStep.step(ORDER_CREATE, ORDER_CANCEL));

        // 2) Apply all matching discounts (0..N)
        steps.addAll(allMatches(ctx, discountScenarios));

        // 3) Payment (exclusive)
        if (!"COD".equalsIgnoreCase(ctx.paymentMethod())) {
            var paymentStep = firstMatch(ctx, paymentScenarios);
            steps.add(paymentStep);
        }

        // 4) Shipping (exclusive)
        var shippingStep = firstMatch(ctx, shippingScenarios);
        if (!"NOOP".equals(shippingStep.action())) {
            steps.add(shippingStep);
        }

        // 5) Confirm order at the end; compensate by rejecting (example)
        steps.add(SagaStep.step(ORDER_CONFIRM, ORDER_REJECT));

        return new SagaPlan(steps);
    }
}
