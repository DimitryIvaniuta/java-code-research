package com.code.research.datastructures.algorithm.search.binarysearch;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class BinarySearchProductApplication {

    /**
     * Main method demonstrating the binary search algorithm on an array of products.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a sorted array of products (sorted by id).
        Product[] products = new Product[]{
                new Product(101, "Laptop", 999.99),
                new Product(102, "Smartphone", 699.99),
                new Product(103, "Tablet", 399.99),
                new Product(104, "Monitor", 199.99),
                new Product(105, "Keyboard", 49.99)
        };

        int targetId = 103;
        Product foundProduct = BinarySearchProduct.binarySearch(products, targetId);
        if (foundProduct != null) {
            log.info("Product found: {}", foundProduct);
        } else {
            log.info("Product with id {} not found.", targetId);
        }

        // Sort the products using Arrays.sort(), which uses the compareTo method of Product.
        Arrays.sort(products);

        log.info("\nSorted Products (by id):");
        for (Product p : products) {
            log.info("{}", p);
        }

    }

}
