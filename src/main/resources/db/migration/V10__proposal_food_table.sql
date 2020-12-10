CREATE TABLE user_orders (
    id bigserial PRIMARY KEY,
    proposal_uuid varchar(36) NOT NULL,
    food_id bigint NOT NULL,
    amount_of_food integer NOT NULL,
);
