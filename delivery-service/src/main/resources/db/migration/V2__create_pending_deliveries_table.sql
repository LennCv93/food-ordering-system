CREATE TABLE pending_deliveries (
    order_id BIGINT PRIMARY KEY,
    delivery_address VARCHAR(255) NOT NULL
);
