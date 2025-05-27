package com.code.research.concurrent.orders.service;

import com.code.research.concurrent.orders.domain.OrderBox;
import com.code.research.concurrent.orders.domain.OrderLabel;
import com.code.research.concurrent.orders.domain.Order;
import com.code.research.concurrent.orders.exception.BoxNotFoundException;
import com.code.research.concurrent.orders.exception.OutOfStockException;
import com.code.research.concurrent.orders.exception.PackingException;
import com.code.research.concurrent.orders.exception.ShippingException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of PackingService.
 * Reserves inventory, boxes the items, prints a shipping label,
 * and hands off to the shipping system.
 */
@Slf4j
public class DefaultPackingService implements PackingService {
    private static final Logger logger = Logger.getLogger(DefaultPackingService.class.getName());

    private final InventoryService inventoryService;
    private final BoxService boxService;
    private final ShippingService shippingService;

    /**
     * @param inventoryService service to reserve and decrement stock
     * @param boxService       service to choose & pack boxes
     * @param shippingService  service to create labels & schedule shipment
     */
    public DefaultPackingService(
            InventoryService inventoryService,
            BoxService boxService,
            ShippingService shippingService) {
        this.inventoryService = Objects.requireNonNull(inventoryService, "inventoryService");
        this.boxService = Objects.requireNonNull(boxService, "boxService");
        this.shippingService = Objects.requireNonNull(shippingService, "shippingService");
    }

    @Override
    public void pack(Order order) throws PackingException {
        logger.info(() -> "Starting to pack order " + order.getId());
        try {
            // 1) Reserve all items in inventory
            inventoryService.reserve(order);
            logger.fine(() -> "Reserved inventory for order " + order.getId());

            // 2) Select and pack into a box
            List<String> items = order.getItems();
            OrderBox box = boxService.chooseBox(items);
            boxService.packItems(box, items);
            logger.fine(() -> "Packed " + items.size() + " items into box " + box.getSize());

            // 3) Generate shipping label
            OrderLabel orderLabel = shippingService.generateLabel(order, box);
            logger.fine(() -> "Generated shipping label " + orderLabel.getTrackingNumber());

            // 4) Hand off for shipment
            shippingService.scheduleShipment(order, box, orderLabel);
            logger.info(() -> "Order " + order.getId() +
                    " packed and scheduled for shipping: " +
                    orderLabel.getTrackingNumber());

        } catch (OutOfStockException | BoxNotFoundException | ShippingException e) {
            throw new PackingException(
                    "Failed to pack order " + order.getId() + ": " + e.getMessage(), e);
        } catch (Exception unexpected) {
            logger.log(Level.SEVERE,
                    "Unexpected error while packing order " + order.getId(),
                    unexpected);
            throw new PackingException(
                    "Unexpected error for order " + order.getId(), unexpected);
        }
    }
}