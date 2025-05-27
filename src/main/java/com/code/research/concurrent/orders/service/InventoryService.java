package com.code.research.concurrent.orders.service;

import com.code.research.concurrent.orders.domain.Order;
import com.code.research.concurrent.orders.exception.OutOfStockException;

public interface InventoryService {
    void reserve(Order order) throws OutOfStockException;
}