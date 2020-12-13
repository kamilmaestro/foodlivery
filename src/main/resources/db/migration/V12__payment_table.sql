CREATE TABLE payments (
    id bigserial PRIMARY KEY,
    uuid varchar(36) NOT NULL,
    purchaser_id bigint NOT NULL,
    payer_id bigint NOT NULL,
    supplier_id bigint NOT NULL,
    channel_id bigint NOT NULL,
    to_pay decimal(15, 5) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    status varchar(25) NOT NULL
);
