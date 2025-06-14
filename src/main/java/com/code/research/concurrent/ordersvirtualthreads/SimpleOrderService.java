package com.code.research.concurrent.ordersvirtualthreads;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class SimpleOrderService implements OrderService  {

    @Override
    public void process(Order order) {
        log.info("Start processing order {}: {} {} on {}",
                order.id(), order.item(), order.quantity(), Thread.currentThread());
        try {
            // simulate I/O or compute
            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Order processing was interrupted", e);
            return;
        }
        log.info("Completed order {} on {}", order.id(), Thread.currentThread());
    }

}
