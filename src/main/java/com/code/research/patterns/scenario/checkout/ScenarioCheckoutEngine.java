package com.code.research.patterns.scenario.checkout;

import java.util.List;
import java.util.Objects;

public final class ScenarioCheckoutEngine<C, R> {

    private final List<ScenarioCheckout<C, R>> scenarios;

    public ScenarioCheckoutEngine(List<ScenarioCheckout<C, R>> scenarios) {
        this.scenarios = List.copyOf(Objects.requireNonNull(scenarios));
    }

    /**
     * Finds the FIRST scenario that matches the context and executes it.
     * Ordering matters: put more specific scenarios first.
     */
    public R execute(C ctx) {
        for (ScenarioCheckout<C, R> scenario : scenarios) {
            if (scenario.matches(ctx)) {
                return scenario.execute(ctx);
            }
        }
        throw new IllegalStateException("No scenario matched context: " + ctx);
    }
}
