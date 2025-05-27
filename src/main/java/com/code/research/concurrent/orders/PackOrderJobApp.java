package com.code.research.concurrent.orders;

import com.code.research.concurrent.orders.domain.Order;
import com.code.research.concurrent.orders.domain.OrderBox;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.code.research.concurrent.orders.service.BoxService;
import com.code.research.concurrent.orders.service.CarrierApiShippingService;
import com.code.research.concurrent.orders.service.DefaultPackingService;
import com.code.research.concurrent.orders.service.DimensionBasedBoxService;
import com.code.research.concurrent.orders.service.InventoryService;
import com.code.research.concurrent.orders.service.JdbcInventoryService;
import com.code.research.concurrent.orders.service.PackingService;
import com.code.research.concurrent.orders.service.ShippingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PackOrderJobApp {

    // Warehouse and worker configuration
    private static final int WAREHOUSE_CAPACITY = 100;
    private static final int WORKER_COUNT       = 5;

    public static void main(String[] args) {
        // 1. Configure HikariCP DataSource
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(System.getenv("JDBC_URL"));
        hikariConfig.setUsername(System.getenv("JDBC_USER"));
        hikariConfig.setPassword(System.getenv("JDBC_PASSWORD"));
        hikariConfig.setMaximumPoolSize(10);

        try (HikariDataSource ds = new HikariDataSource(hikariConfig)) {
            // 2. Initialize domain services
            InventoryService inventoryService = new JdbcInventoryService(ds);
            List<DimensionBasedBoxService.BoxDefinition> catalog = List.of(
                    new DimensionBasedBoxService.BoxDefinition("Small",  3),
                    new DimensionBasedBoxService.BoxDefinition("Medium", 5),
                    new DimensionBasedBoxService.BoxDefinition("Large", 10)
            );
            BoxService boxService = new DimensionBasedBoxService(catalog);
            ShippingService shippingService = new CarrierApiShippingService(new DummyCarrierClient());

            PackingService packingService = new DefaultPackingService(
                    inventoryService, boxService, shippingService
            );

            // 3. Create the shared Warehouse
            Warehouse warehouse = new Warehouse(WAREHOUSE_CAPACITY);

            // 4. Start packing worker threads
            ExecutorService workerPool = Executors.newFixedThreadPool(WORKER_COUNT);
            try (PackOrderExecutorWrapper executorWrapper = new PackOrderExecutorWrapper(workerPool)) {
                for (int i = 0; i < WORKER_COUNT; i++) {
                    workerPool.submit(new PackOrderJob(warehouse, packingService));
                }
            }

            // 5. Produce sample orders
            List<Order> sampleOrders = List.of(
                    new Order("ORD-001", List.of("SKU-A", "SKU-B")),
                    new Order("ORD-002", List.of("SKU-C")),
                    new Order("ORD-003", List.of("SKU-A", "SKU-C", "SKU-D"))
            );
            for (Order order : sampleOrders) {
                try {
                    warehouse.submitOrder(order);
                    log.info("Submitted order: " + order.getId());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Interrupted while submitting orders");
                    break;
                }
            }

            // 6. Shutdown worker pool after all orders processed
            workerPool.shutdown();
            if (!workerPool.awaitTermination(2, TimeUnit.MINUTES)) {
                workerPool.shutdownNow();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Worker pool interrupted during shutdown");
        }
    }
    /**
     * Dummy CarrierApiClient implementation for demonstration.
     */
    private static class DummyCarrierClient implements CarrierApiShippingService.CarrierApiClient {
        @Override
        public String createShipment(Order order, OrderBox box) {
            String tracking = UUID.randomUUID().toString();
            log.info("Created mock shipment {} for order {}", tracking, order.getId());
            return tracking;
        }

        @Override
        public void scheduleDelivery(String trackingNumber) {
            log.info("Scheduled mock delivery for tracking {}", trackingNumber);
        }
    }
}
