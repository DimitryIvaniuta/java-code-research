package com.code.research.algorithm.tasks;

public class Product {

    public enum Category {
        ELECTRONICS, BOOKS, GROCERY
    }


    private final String name;
    private final Category category;

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return String.format("Product{name='%s', category=%s}", name, category);
    }
}