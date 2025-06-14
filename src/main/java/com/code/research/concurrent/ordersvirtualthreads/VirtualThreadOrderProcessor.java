package com.code.research.concurrent.ordersvirtualthreads;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class VirtualThreadOrderProcessor implements AutoCloseable {

    private final ExecutorService executor;

    private final OrderService orderService;

    public VirtualThreadOrderProcessor(OrderService orderService) {
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.orderService = orderService;
    }

    /**
     * Submits one virtual-thread task per order and waits for all to finish.
     * Any exceptions in processing are logged; interruption stops waiting early.
     */
    public void processAll(List<Order> orders) {
        List<Future<?>> futures = new ArrayList<>(orders.size());
        for (Order order : orders) {
            futures.add(
                    executor.submit(() -> orderService.process(order))
            );
        }

        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (ExecutionException ee) {
                log.info("Error in order task", ee.getCause());
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.info("Processor interrupted while waiting", ie);
                break;
            }
        }
    }

    /**
     * Shuts down the executor, waiting up to 60s for clean termination,
     * then forcing shutdown if necessary.
     */
    @Override
    public void close() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                    log.info("Virtual-thread executor did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
