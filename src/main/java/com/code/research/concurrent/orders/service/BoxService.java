package com.code.research.concurrent.orders.service;

import com.code.research.concurrent.orders.domain.OrderBox;
import com.code.research.concurrent.orders.exception.BoxNotFoundException;

import java.util.List;

/**
 * Chooses an appropriate box and packs items into it.
 */
public interface BoxService {
    /**
     * Chooses a box based on item dimensions/weight.
     */
    OrderBox chooseBox(List<String> itemSkus) throws BoxNotFoundException;

    /**
     * Packs the given SKUs into the box.
     */
    void packItems(OrderBox box, List<String> itemSkus);
}