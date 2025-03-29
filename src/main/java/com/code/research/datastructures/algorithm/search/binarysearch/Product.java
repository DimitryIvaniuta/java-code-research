package com.code.research.datastructures.algorithm.search.binarysearch;

/**
 * The Product class represents an item in a catalog with a unique ID, name, and price.
 */
public class Product implements Comparable<Product> {

    /** The unique identifier for the product. */
    private final int id;

    /** The name of the product. */
    private final String name;

    /** The price of the product. */
    private final double price;

    /**
     * Constructs a new Product with the specified id, name, and price.
     *
     * @param id    the unique identifier of the product
     * @param name  the name of the product
     * @param price the price of the product
     */
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the product's id.
     *
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the product's name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the product's price.
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Compares this product with another product based on their ids.
     *
     * @param other the other product to compare to
     * @return a negative integer, zero, or a positive integer as this product's id
     *         is less than, equal to, or greater than the other product's id
     */
    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', price=%.2f}", id, name, price);
    }

}
