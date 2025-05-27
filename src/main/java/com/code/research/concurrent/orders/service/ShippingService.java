package com.code.research.concurrent.orders.service;

import com.code.research.concurrent.orders.domain.OrderBox;
import com.code.research.concurrent.orders.domain.OrderLabel;
import com.code.research.concurrent.orders.domain.Order;
import com.code.research.concurrent.orders.exception.ShippingException;

/**
 * Generates labels and schedules the shipment.
 */
public interface ShippingService {
    /**
     * Creates a shipping label (with tracking number, etc.).
     */
    OrderLabel generateLabel(Order order, OrderBox orderBox) throws ShippingException;

    /**
     * Hands the package off to the carrier or scheduling system.
     */
    void scheduleShipment(Order order, OrderBox orderBox, OrderLabel orderLabel) throws ShippingException;
}