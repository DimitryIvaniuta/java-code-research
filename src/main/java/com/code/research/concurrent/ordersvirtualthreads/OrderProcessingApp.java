package com.code.research.concurrent.ordersvirtualthreads;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OrderProcessingApp {

    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order(1, "Book", 2),
                new Order(2, "Pen", 10),
                new Order(3, "Laptop", 1),
                new Order(4, "Headphones", 5),
                new Order(5, "Notebook", 7)
        );

        log.info("Starting order processing with virtual threads...");
        try (VirtualThreadOrderProcessor processor =
                     new VirtualThreadOrderProcessor(new SimpleOrderService())) {
            processor.processAll(orders);
        }
        log.info("All orders have been processed.");
    }

}
