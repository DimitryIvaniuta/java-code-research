package com.code.research.livecoding.streams;

import com.code.research.livecoding.streams.dto.Order;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class SalesByRegion {

    /**
     * Groups orders by region into a LinkedHashMap (to preserve insertion order),
     * and sums the order totals per region.
     *
     * @param orders the input list of orders
     * @return a LinkedHashMap where keys are regions (in encountered order)
     * and values are the sum of totals in that region
     */
    public static Map<String, Double> salesByRegion(List<Order> orders) {
        return orders.stream()
                .collect(
                        Collectors.groupingBy(
                                Order::getRegion,
                                LinkedHashMap::new,
                                Collectors.summingDouble(Order::getTotal)
                        )
                );
    }

    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("EMEA", 120.50),
                new Order("APAC", 75.00),
                new Order("EMEA", 30.00),
                new Order("AMER", 200.00),
                new Order("APAC", 100.00),
                new Order("EMEA", 49.99)
        );

        Map<String, Double> result = salesByRegion(orders);
        result.forEach((region, sum) ->
                System.out.printf("%-4s → €%.2f%n", region, sum)
        );
        // Expected output:
        // EMEA → €200.49
        // APAC → €175.00
        // AMER → €200.00
    }

}
