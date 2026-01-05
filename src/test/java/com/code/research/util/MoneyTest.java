package com.code.research.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void add_sameCurrency_sumsAmount() {
        Money a = Money.of(10, "USD");
        Money b = Money.of(5, "USD");

        Money result = a.add(b);

        assertEquals(Money.of(15, "USD"), result);
    }

    @Test
    void add_differentCurrency_throws() {
        Money a = Money.of(10, "USD");
        Money b = Money.of(5, "EUR");

        assertThrows(IllegalArgumentException.class, () -> a.add(b));
    }
}
