-- customers table
CREATE TABLE customers
(
    customer_id BIGINT NOT NULL PRIMARY KEY,
    name        VARCHAR(100),
    email       VARCHAR(100)
);