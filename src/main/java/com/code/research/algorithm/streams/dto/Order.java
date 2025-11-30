package com.code.research.algorithm.streams.dto;

public class Order {

    private final String region;
    private final double total;

    public Order(String region, double total) {
        this.region = region;
        this.total = total;
    }

    public String getRegion() {
        return region;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return String.format("Order[%s: %.2f]", region, total);
    }

}
