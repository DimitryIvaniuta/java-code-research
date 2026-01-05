package com.code.research.service.reserveorder;

/**
 * Map to 404 in controller advice
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("Order not found: " + orderId);
    }
}
