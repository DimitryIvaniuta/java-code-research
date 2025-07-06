package com.code.research.livecoding.streams;

import com.code.research.livecoding.streams.dto.OrderAmount;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MaxOrderAmount {

    /**
     * Finds the maximum order amount from the given list of orders.
     *
     * @param orders the input List<Order>
     * @return an Optional containing the maximum amount if the list is non-empty,
     *         otherwise empty
     */
    public static Optional<Double> findMaxOrderAmount(List<OrderAmount> orders) {
        return orders.stream()
                .map(OrderAmount::getAmount)
                .max(Comparator.naturalOrder());
    }

    public static void main(String[] args) {
        List<OrderAmount> orders = List.of(
                new OrderAmount(1001L, 120.50),
                new OrderAmount(1002L,  75.00),
                new OrderAmount(1003L, 200.25),
                new OrderAmount(1004L,  10.00)
        );

        Optional<Double> maxAmount = findMaxOrderAmount(orders);
        maxAmount.ifPresentOrElse(
                amt -> System.out.printf("Max order amount: %.2f%n", amt),
                ()  -> System.out.println("No orders available")
        );
        // Expected output: Max order amount: 200.25
    }
}
