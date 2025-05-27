package com.code.research.concurrent.orders.service;

import com.code.research.concurrent.orders.domain.Order;
import com.code.research.concurrent.orders.exception.PackingException;

/**
 * A simple service interface abstraction for packing logic.
 */
public interface PackingService {

    /**
     * Packs the given order, reserving inventory and preparing shipment.
     *
     * @param order the order to pack
     * @throws PackingException on recoverable packing failures
     */
    void pack(Order order) throws PackingException;

}