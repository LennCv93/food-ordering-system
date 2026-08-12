CREATE TABLE deliveries (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE,
    delivery_address VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('ASSIGNED','IN_TRANSIT','DELIVERED','FAILED')),
    courier_name VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);
