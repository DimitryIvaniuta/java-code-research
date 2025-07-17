package com.code.research.livecoding.streams.denomination;

import java.util.stream.Collectors;

/**
 * Renders a Change into a human‑readable breakdown.
 */
public class DenominationChangeFormatter {

    /**
     * e.g. "$20 x2 + $5 x1 + $1 x3"
     */
    public static String formatWithCounts(DenominationChange change) {
        return change.getCounts().entrySet().stream()
                .map(e -> "$" + e.getKey().getValue().intValue() + " x" + e.getValue())
                .collect(Collectors.joining(" + "));
    }

    /**
     * e.g. "$20 + $20 + $5 + $1 + $1 + $1"
     */
    public static String formatFlatList(DenominationChange change) {
        return change.toFlatList().stream()
                .map(bd -> "$" + bd.intValue())
                .collect(Collectors.joining(" + "));
    }
}
