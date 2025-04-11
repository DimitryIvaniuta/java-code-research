package com.code.research.patterns.singleton;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Integer> inventory;

    private InventoryManager() {
        inventory = new HashMap<>();
        // Initialize inventory: product IDs and their available counts.
        inventory.put("P001", 100);
        inventory.put("P002", 50);
        inventory.put("P003", 75);
    }

    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public boolean reserveProduct(String productId, int quantity) {
        int available = inventory.getOrDefault(productId, 0);
        if (available >= quantity) {
            inventory.put(productId, available - quantity);
            log.info("Reserved {} of product {}. Remaining: {}", quantity, productId, (available - quantity));
            return true;
        } else {
            log.info("Not enough inventory for product {}", productId);
            return false;
        }
    }

    public int getInventory(String productId) {
        return inventory.getOrDefault(productId, 0);
    }
}
