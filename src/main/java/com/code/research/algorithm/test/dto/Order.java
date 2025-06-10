package com.code.research.algorithm.test.dto;

public record Order(OrderStatus status) {

    public enum OrderStatus {
        PENDING,
        APPROVED,
        REJECTED,
        DELIVERED
    }
}
