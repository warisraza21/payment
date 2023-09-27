CREATE SCHEMA `payment_gateway`;

use payment_gateway;

CREATE TABLE merchant (
    merchant_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    business_type VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(20)
);

CREATE TABLE payment (
    payment_id VARCHAR(255) PRIMARY KEY,
    merchant_id VARCHAR(255),
    amount DOUBLE,
    currency VARCHAR(255),
    order_id VARCHAR(255),
    status VARCHAR(255),
    FOREIGN KEY (merchant_id) REFERENCES merchant (merchant_id)
);