package com.code.research.livecoding.streams.denomination;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * All supported denominations, in descending order.
 */
public enum Denomination {
    HUNDRED(new BigDecimal("100")),
    FIFTY   (new BigDecimal("50")),
    TWENTY  (new BigDecimal("20")),
    TEN     (new BigDecimal("10")),
    FIVE    (new BigDecimal("5")),
    ONE     (new BigDecimal("1"));

    private final BigDecimal value;

    Denomination(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    /**
     * @return all denominations sorted descending by value
     */
    public static List<Denomination> descending() {
        return Arrays.asList(values());
    }
}
