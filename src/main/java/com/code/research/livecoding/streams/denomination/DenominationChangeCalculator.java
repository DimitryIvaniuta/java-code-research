package com.code.research.livecoding.streams.denomination;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DenominationChangeCalculator {
    private final List<Denomination> denominations = Denomination.descending();

    /**
     * @param amount the change to give back (non‑negative)
     * @return a Change object recording how many of each denom to dispense
     */
    public DenominationChange calculateChange(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be non‑negative");
        }

        DenominationChange change = new DenominationChange();
        BigDecimal remaining = amount.setScale(0, RoundingMode.DOWN);

        for (Denomination d : denominations) {
            BigDecimal[] divRem = remaining.divideAndRemainder(d.getValue());
            int count = divRem[0].intValue();
            if (count > 0) {
                change.put(d, count);
                remaining = divRem[1];
            }
        }
        return change;
    }
}
