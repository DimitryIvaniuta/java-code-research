package com.code.research.algorithm.test.streams;

import com.code.research.algorithm.test.dto.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartitionByPrice {

    private static Map<Boolean, List<Product>> partitionByPrice(
            List<Product> products,
            BigDecimal priceThreshold
    ) {
        Objects.requireNonNull(products, "products is null");
        Objects.requireNonNull(priceThreshold, "priceThreshold is null");

        return products.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.partitioningBy(
                        p -> p.getPrice() != null && p.getPrice().compareTo(priceThreshold) > 0
                ));
    }

    // Example usage
    public static void main(String[] args) {
        List<Product> catalog = List.of(
                new Product("Laptop", Product.Category.ELECTRONICS, BigDecimal.valueOf(1_200.00)),
                new Product("Notebook", Product.Category.ELECTRONICS, BigDecimal.valueOf(15.50)),
                new Product("Smartphone", Product.Category.ELECTRONICS, BigDecimal.valueOf(799.99)),
                new Product("Pencil", Product.Category.TOOLS, BigDecimal.valueOf(0.99)),
                new Product("Desk Chair", Product.Category.ACCESSORY, BigDecimal.valueOf(150.00))
        );

        BigDecimal threshold = BigDecimal.valueOf(100.00);
        Map<Boolean, List<Product>> partitioned = partitionByPrice(catalog, threshold);

        System.out.println("Expensive products (> " + threshold + "):");
        partitioned.get(true).forEach(p ->
                System.out.println("  - " + p.getName() + " ($" + p.getPrice() + ")")
        );

        System.out.println("\nCheap products (≤ " + threshold + "):");
        partitioned.get(false).forEach(p ->
                System.out.println("  - " + p.getName() + " ($" + p.getPrice() + ")")
        );
    }
}
