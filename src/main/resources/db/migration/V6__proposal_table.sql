CREATE TABLE proposals (
    id bigserial PRIMARY KEY,
    uuid varchar(36) NOT NULL,
    created_by bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    expiration_date timestamp with time zone NOT NULL,
    supplier_id bigint NOT NULL,
    channel_id bigint NOT NULL,
    status varchar(25) NOT NULL
);
