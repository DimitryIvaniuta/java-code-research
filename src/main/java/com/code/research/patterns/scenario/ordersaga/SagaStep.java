package com.code.research.patterns.scenario.ordersaga;

/**
 * A step knows its "do" action and the compensation action if later steps fail.
 * In practice, you publish commands/events like PAYMENT_AUTHORIZE and on failure PAYMENT_CANCEL.
 */
public record SagaStep(String action, String compensateAction) {
    public static SagaStep step(String action, String compensateAction) {
        return new SagaStep(action, compensateAction);
    }
}