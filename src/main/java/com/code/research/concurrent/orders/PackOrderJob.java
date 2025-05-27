package com.code.research.concurrent.orders;

import com.code.research.concurrent.orders.domain.Order;
import com.code.research.concurrent.orders.exception.PackingException;
import com.code.research.concurrent.orders.service.PackingService;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class PackOrderJob implements Runnable {

    private final Warehouse warehouse;
    private final PackingService packingService;

    /**
     * @param warehouse      the shared warehouse to retrieve orders from; must not be null
     * @param packingService the service that actually does the packing; must not be null
     */
    public PackOrderJob(Warehouse warehouse, PackingService packingService) {
        this.warehouse = Objects.requireNonNull(warehouse, "warehouse");
        this.packingService = Objects.requireNonNull(packingService, "packingService");
    }

    /**
     * Continuously retrieves orders and packs them until the thread is interrupted.
     */
    @Override
    public void run() {
        log.info("PackOrderJob started on thread {}", Thread.currentThread().getName());
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Block until an order is available
                Order order = warehouse.retrieveOrder();
                log.info("Retrieved order {} with items {}", order.getId(), order.getItems());
                doPackOrder(order);
            }
        } catch (InterruptedException ie) {
            // Graceful shutdown on interrupt
            log.info("PackOrderJob interrupted; shutting down");
            Thread.currentThread().interrupt();
        }
        log.info("PackOrderJob terminated on thread " + Thread.currentThread().getName());
    }

    private void doPackOrder(Order order) {
        try {
            // Delegate to the packing service
            packingService.pack(order);
            log.info("Successfully packed order {}", order.getId());
        } catch (PackingException pe) {
            // Handle recoverable packing errors (e.g., inventory shortage)
            log.info("Failed to pack order {}: {}", order.getId(), pe.getMessage(), pe);
            // Optionally re-submit or move to a dead-letter queue
        } catch (Exception ex) {
            // Catch-all for unexpected errors
            log.error("Unexpected error while packing order " + order.getId(), ex);
        }
    }
}

