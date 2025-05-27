package com.code.research.concurrent.orders.service;

import com.code.research.concurrent.orders.domain.Order;
import com.code.research.concurrent.orders.domain.OrderBox;
import com.code.research.concurrent.orders.domain.OrderLabel;
import com.code.research.concurrent.orders.exception.ShippingException;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * ShippingService implementation that calls out to a carrier API.
 */
public class CarrierApiShippingService implements ShippingService {
    private static final Logger logger = Logger.getLogger(CarrierApiShippingService.class.getName());
    private final CarrierApiClient client;

    public CarrierApiShippingService(CarrierApiClient client) {
        this.client = Objects.requireNonNull(client, "client");
    }

    @Override
    public OrderLabel generateLabel(Order order, OrderBox box) throws ShippingException {
        try {
            String tracking = client.createShipment(order, box);
            logger.fine(() -> "Carrier created shipment " + tracking + " for order " + order.getId());
            return new OrderLabel(tracking);
        } catch (Exception e) {
            throw new ShippingException("Error generating label: " + e.getMessage(), e);
        }
    }

    @Override
    public void scheduleShipment(Order order, OrderBox box, OrderLabel label) throws ShippingException {
        try {
            client.scheduleDelivery(label.getTrackingNumber());
            logger.info(() -> "Scheduled shipment " + label.getTrackingNumber() +
                    " for order " + order.getId());
        } catch (Exception e) {
            throw new ShippingException("Error scheduling shipment: " + e.getMessage(), e);
        }
    }

    /**
     * Low-level carrier API client abstraction.
     */
    public interface CarrierApiClient {
        /**
         * Creates a shipment and returns its tracking number.
         */
        String createShipment(Order order, OrderBox box) throws Exception;

        /**
         * Schedules the delivery (e.g. calls a different endpoint or confirms pickup).
         */
        void scheduleDelivery(String trackingNumber) throws Exception;
    }
}
