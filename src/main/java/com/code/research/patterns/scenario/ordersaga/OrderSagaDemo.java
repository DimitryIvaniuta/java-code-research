package com.code.research.patterns.scenario.ordersaga;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class OrderSagaDemo {
    public static void main(String[] args) {
        var builder = new OrderSagaPlanBuilder(
                List.of(
                        new VipDiscountScenario(),
                        new CouponDiscountScenario(),
                        new HighValueDiscountScenario()
                ),
                List.of(
                        new CardPaymentScenario(),
                        new PaypalPaymentScenario()
                ),
                List.of(
                        new DigitalOnlyNoShippingScenario(),
                        new CourierShippingScenario(),
                        new PickupShippingScenario()
                )
        );

        var ctx = new OrderSagaContext(
                "corr-1",
                "ORD-123",
                "VIP",
                "CARD",
                "COURIER",
                "PL",
                new BigDecimal("6500"),
                Set.of("WELCOME10"),
                List.of(new OrderItem("SKU-1", 1, false))
        );

        var plan = builder.build(ctx);
        System.out.println(plan.steps());
    }
}
