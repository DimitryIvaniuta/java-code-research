-- denormalized_orders table that combines customer, order, and product details.
CREATE TABLE denormalized_orders
(
    order_id       BIGINT    NOT NULL,
    customer_id    BIGINT    NOT NULL,
    customer_name  VARCHAR(100),
    customer_email VARCHAR(100),
    order_date     TIMESTAMP NOT NULL default current_timestamp,
    product_id     BIGINT    NOT NULL,
    product_name   VARCHAR(100),
    product_price  DECIMAL(10, 2),
    quantity       BIGINT    NOT NULL,
    PRIMARY KEY (order_id, product_id) -- Assuming each order has unique products
);
