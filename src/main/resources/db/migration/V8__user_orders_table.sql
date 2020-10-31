CREATE TABLE user_orders (
    id bigserial PRIMARY KEY,
    order_uuid varchar(36) NOT NULL,
    food_id bigint NOT NULL,
    amount_of_food integer NOT NULL,
    ordered_for bigint NOT NULL
);
