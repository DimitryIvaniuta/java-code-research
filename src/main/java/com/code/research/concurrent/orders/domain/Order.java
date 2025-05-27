package com.code.research.concurrent.orders.domain;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public final class Order {
    private final String id;
    private final List<String> items;

    public Order(final String id, final List<String> items) {
        this.id = Objects.requireNonNull(id);
        this.items = List.copyOf(Objects.requireNonNull(items, "items"));
    }


    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", items=" + items +
                '}';
    }
}
