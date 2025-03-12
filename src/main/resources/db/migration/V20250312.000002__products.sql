-- products table
CREATE TABLE products
(
    product_id   BIGINT NOT NULL PRIMARY KEY,
    product_name TEXT,
    price        DECIMAL(10, 2)
);
