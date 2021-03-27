CREATE TABLE orders (
    id bigserial PRIMARY KEY,
    uuid varchar(36) NOT NULL,
    supplier_id bigint NOT NULL,
    channel_id bigint NOT NULL,
    purchaser_id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    status varchar(20) NOT NULL
);
