package com.code.research.string;

import lombok.Getter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class NumberStats {

    private final int sum;
    private final Map<Integer,Integer> counts;

    public NumberStats() {
        this.sum = 0;
        this.counts = Collections.emptyMap();
    }

    public NumberStats(final int sum, final Map<Integer, Integer> counts) {
        this.sum = sum;
        // Use LinkedHashMap to preserve insertion order of first appearance
        this.counts = Collections.unmodifiableMap(counts);
    }

    /** Returns a new stats object with `n` added. */
    public NumberStats withAdded(int n) {
        Map<Integer,Integer> newCounts = new LinkedHashMap<>(counts);
        newCounts.merge(n, 1, Integer::sum);
        return new NumberStats(sum, newCounts);
    }

}
