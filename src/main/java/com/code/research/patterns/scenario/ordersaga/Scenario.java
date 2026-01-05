package com.code.research.patterns.scenario.ordersaga;

public interface Scenario<C, R> {
    String name();

    /**
     * True when this scenario should handle the request.
     */
    boolean matches(C ctx);

    /**
     * Returns scenario-specific result (here: a SagaPlan).
     */
    R execute(C ctx);
}
