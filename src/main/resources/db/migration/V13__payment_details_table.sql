CREATE TABLE payment_details (
    id bigserial PRIMARY KEY,
    payment_uuid varchar(36) NOT NULL,
    food_name varchar(255) NOT NULL,
    amount_of_food integer NOT NULL,
    food_price decimal(15, 5) NOT NULL
);
