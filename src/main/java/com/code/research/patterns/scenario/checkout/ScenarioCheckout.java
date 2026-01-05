package com.code.research.patterns.scenario.checkout;

/**
 * Scenario Pattern:
 * - matches(ctx): decides if this scenario should handle the request
 * - execute(ctx): contains the scenario-specific logic
 *
 * This avoids huge if/else or switch blocks spread across services.
 *
 *
 * Different checkout rules for:
 * VIP + Card in EU → discount + require 3DS
 * Regular + PayPal → no 3DS, different gateway
 * High amount → extra risk checks
 */
public interface ScenarioCheckout<C, R> {
    String name();

    /** Return true if this scenario applies to the provided context. */
    boolean matches(C ctx);

    /** Execute scenario-specific logic and return result. */
    R execute(C ctx);
}
