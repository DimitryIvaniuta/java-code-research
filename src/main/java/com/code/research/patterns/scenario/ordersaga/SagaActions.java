package com.code.research.patterns.scenario.ordersaga;

public final class SagaActions {
    private SagaActions() {
    }

    // Order
    public static final String ORDER_CREATE = "ORDER_CREATE";
    public static final String ORDER_CANCEL = "ORDER_CANCEL";
    public static final String ORDER_CONFIRM = "ORDER_CONFIRM";
    public static final String ORDER_REJECT = "ORDER_REJECT";

    // Discounts
    public static final String DISCOUNT_APPLY = "DISCOUNT_APPLY";
    public static final String DISCOUNT_REVERT = "DISCOUNT_REVERT";

    // Payment
    public static final String PAYMENT_AUTHORIZE = "PAYMENT_AUTHORIZE";
    public static final String PAYMENT_CANCEL = "PAYMENT_CANCEL";

    // Shipping
    public static final String SHIPPING_RESERVE = "SHIPPING_RESERVE";
    public static final String SHIPPING_RELEASE = "SHIPPING_RELEASE";
}
