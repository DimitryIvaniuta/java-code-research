package com.code.research.algorithm.streams.dto;

public class Transaction {

    private final String currency;
    private final double amount;

    public Transaction(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

}
