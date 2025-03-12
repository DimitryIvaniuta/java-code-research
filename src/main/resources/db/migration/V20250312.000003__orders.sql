-- orders table: each order belongs to a customer
CREATE TABLE orders
(
    order_id    BIGINT NOT NULL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_date  TIMESTAMP NOT NULL default current_timestamp,
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);