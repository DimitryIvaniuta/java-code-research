-- order_items table: each record represents a product in an order
CREATE TABLE order_items
(
    order_item_id BIGINT NOT NULL PRIMARY KEY,
    order_id      BIGINT NOT NULL,
    product_id    BIGINT NOT NULL,
    quantity      INT,
    FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (product_id) REFERENCES products (product_id)
);