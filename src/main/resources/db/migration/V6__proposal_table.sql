CREATE TABLE proposals (
    id bigserial PRIMARY KEY,
    created_by bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    supplier_id bigint NOT NULL,
    food_id bigint NOT NULL,
    amount_of_food integer NOT NULL,
    channel_id bigint NOT NULL
);
