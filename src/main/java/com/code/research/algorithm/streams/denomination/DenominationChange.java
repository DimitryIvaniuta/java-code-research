package com.code.research.algorithm.streams.denomination;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holds the result of a change calculation:
 * for each denomination, how many to give.
 */
public class DenominationChange {
    private final Map<Denomination, Integer> counts = new EnumMap<>(Denomination.class);

    /** Record that we need `count` of the given denomination. */
    public void put(Denomination denom, int count) {
        if (count > 0) counts.put(denom, count);
    }

    /** @return unmodifiable view of denomination→count */
    public Map<Denomination,Integer> getCounts() {
        return Collections.unmodifiableMap(counts);
    }

    /**
     * @return a flat list like [100,100,20,5,1]
     */
    public List<BigDecimal> toFlatList() {
        return counts.entrySet().stream()
                .flatMap(e -> Collections.nCopies(e.getValue(),
                        e.getKey().getValue()).stream())
                .collect(Collectors.toList());
    }
}
