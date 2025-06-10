package com.code.research.algorithm.test.streams;

import com.code.research.algorithm.test.dto.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CountDeliveredOrders {

    /**
     * Counts how many orders have status "DELIVERED".
     *
     * @param orders the input list of Order objects
     * @return the count of orders whose status equals "DELIVERED"
     */
    public static long countDelivered(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.status() == Order.OrderStatus.DELIVERED)
                .count();
    }

    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order(Order.OrderStatus.PENDING),
                new Order(Order.OrderStatus.DELIVERED),
                new Order(Order.OrderStatus.APPROVED),
                new Order(Order.OrderStatus.DELIVERED),
                new Order(Order.OrderStatus.REJECTED)
        );

        long deliveredCount = countDelivered(orders);
        log.info("Number of delivered orders: {}", deliveredCount);
        // Expected output: Number of delivered orders: 2
    }
}
