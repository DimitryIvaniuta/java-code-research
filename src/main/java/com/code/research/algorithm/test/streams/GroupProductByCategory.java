package com.code.research.algorithm.test.streams;

import com.code.research.algorithm.test.dto.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroupProductByCategory {

    /**
     * Groups the given list of products by their category.
     *
     * @param products the list of Product objects (must not be null)
     * @return a Map where each key is a category name and the value is
     * the list of products in that category; never null
     * @throws NullPointerException if {@code products} is null
     */
    public static Map<Product.Category, List<Product>> groupProductByCategory(List<Product> products) {
        return products.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.groupingBy(Product::getCategory, Collectors.toList())
                );

    }

    public static void main(String[] args) {
        List<Product> catalog = List.of(
                new Product("Laptop", Product.Category.ELECTRONICS, BigDecimal.valueOf(100)),
                new Product("Hammer", Product.Category.TOOLS, BigDecimal.valueOf(100)),
                new Product("Smartphone", Product.Category.ELECTRONICS, BigDecimal.valueOf(100)),
                new Product("Drill", Product.Category.TOOLS, BigDecimal.valueOf(100)),
                new Product("Novel", Product.Category.BOOKS, BigDecimal.valueOf(100))
        );

        Map<Product.Category, List<Product>> byCategory = groupProductByCategory(catalog);
        byCategory.forEach((category, items) -> {
            System.out.println(category + ":");
            items.forEach(p -> System.out.println("  - " + p.getName()));
        });

    }

}
