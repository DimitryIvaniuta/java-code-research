package com.code.research.concurrent.orders.domain;

import java.util.Objects;

/**
 * Represents a shipping box with a specific size designation.
 */
public final class OrderBox {
    private final String size;

    /**
     * @param size the box size designation (e.g., "Small", "Medium", "Large"), non-null and non-empty.
     */
    public OrderBox(String size) {
        this.size = Objects.requireNonNull(size, "size");
        if (size.isBlank()) {
            throw new IllegalArgumentException("size must not be blank");
        }
    }

    /**
     * Gets the size designation of the box.
     */
    public String getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderBox)) return false;
        OrderBox box = (OrderBox) o;
        return size.equals(box.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }

    @Override
    public String toString() {
        return "Box{size='" + size + "'}";
    }
}
