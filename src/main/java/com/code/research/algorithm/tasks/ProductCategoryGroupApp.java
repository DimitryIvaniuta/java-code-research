package com.code.research.algorithm.tasks;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ProductCategoryGroupApp {

    /**
     * Groups the given list of products by their category.
     *
     * @param products the list of products to group
     * @return a map from Category to list of Products in that category
     */
    public static Map<Product.Category, List<Product>> groupByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        // downstream collector to make a List<Product>
                        Collectors.mapping(Function.identity(), Collectors.toList())
                ));
    }

    public Map<Product.Category, List<String>> mapByProductName(List<Product> products) {
        return products.stream()
                .collect(
                        Collectors.groupingBy(Product::getCategory,
                                Collectors.mapping(Product::getName, Collectors.toList()))
                );

    }

    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product("Laptop", Product.Category.ELECTRONICS),
                new Product("Smartphone", Product.Category.ELECTRONICS),
                new Product("Bread", Product.Category.GROCERY),
                new Product("Milk", Product.Category.GROCERY),
                new Product("Effective Java", Product.Category.BOOKS),
                new Product("Clean Code", Product.Category.BOOKS)
        );

        Map<Product.Category, List<Product>> byCategory = groupByCategory(products);

        byCategory.forEach((category, list) -> {
            log.info("{}:", category);
            list.forEach(p -> log.info("  - {}", p));
        });
    }


}
