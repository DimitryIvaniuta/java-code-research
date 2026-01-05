package com.code.research.patterns.scenario.ordersaga;

import java.util.Comparator;
import java.util.List;

public final class ScenarioEngines {
    private ScenarioEngines() {}

    /** Picks FIRST matching scenario (exclusive routing). */
    public static <C, R> R firstMatch(C ctx, List<? extends OrderedScenario<C, R>> scenarios) {
        return scenarios.stream()
                .sorted(Comparator.comparingInt(OrderedScenario::order))
                .filter(s -> s.matches(ctx))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No scenario matched context: " + ctx))
                .execute(ctx);
    }

    /** Runs ALL matching scenarios (multi-match) and concatenates results. */
    public static <C> List<SagaStep> allMatches(C ctx, List<? extends OrderedScenario<C, List<SagaStep>>> scenarios) {
        return scenarios.stream()
                .sorted(Comparator.comparingInt(OrderedScenario::order))
                .filter(s -> s.matches(ctx))
                .flatMap(s -> s.execute(ctx).stream())
                .toList();
    }
}
