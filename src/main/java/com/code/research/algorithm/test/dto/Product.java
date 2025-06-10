package com.code.research.algorithm.test.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class Product {

    public enum Category {
        BOOKS,
        ELECTRONICS,
        TOOLS,
        ACCESSORY
    }

    private final Category category;

    private final String name;

    private final BigDecimal price;

    public Product(String name, Category category, BigDecimal price) {
        this.name = Objects.requireNonNull( name, "category cannot be null" );
        this.category = Objects.requireNonNull( category, "category cannot be null" );
        this.price = Objects.requireNonNull( price, "price cannot be null" );
    }

}
