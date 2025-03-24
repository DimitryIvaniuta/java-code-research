package com.code.research.datastructures.arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductInventory {

    public static void main(String[] args) {
        // Array of product IDs
        int[] productIDs = {101, 102, 103, 104, 105};

        for (int id : productIDs) {
            log.info("Product ID: {}", id);
        }
    }

}
