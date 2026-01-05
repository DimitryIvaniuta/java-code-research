package com.code.research.util;

import java.util.Objects;

public final class Money {
    private final long amount;
    private final String currency;

    private Money(long amount, String currency) {
        this.amount = amount;
        this.currency = Objects.requireNonNull(currency);
    }

    public static Money of(long amount, String currency) {
        return new Money(amount, currency);
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Different currency");
        }
        return new Money(this.amount + other.amount, this.currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money m)) return false;
        return amount == m.amount && currency.equals(m.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
