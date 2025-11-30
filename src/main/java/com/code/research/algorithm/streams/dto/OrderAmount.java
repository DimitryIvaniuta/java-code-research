package com.code.research.algorithm.streams.dto;

public class OrderAmount {
    private final Long id;
    private final double amount;

    public OrderAmount(Long id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
}
