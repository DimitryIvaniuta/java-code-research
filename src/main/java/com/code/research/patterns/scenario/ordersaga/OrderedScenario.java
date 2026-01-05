package com.code.research.patterns.scenario.ordersaga;

/**
 * Adds deterministic ordering to scenarios. Lower = earlier.
 */
public interface OrderedScenario<C, R> extends Scenario<C, R> {
    int order();
}