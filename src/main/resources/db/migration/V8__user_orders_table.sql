CREATE TABLE user_orders (
    id bigserial PRIMARY KEY,
    uuid varchar(36) NOT NULL,
    order_uuid varchar(36) NOT NULL,
    ordered_for bigint NOT NULL.
    created_at timestamp with time zone NOT NULL
);
