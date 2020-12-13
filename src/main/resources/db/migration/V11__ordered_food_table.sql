CREATE TABLE ordered_food (
    id bigserial PRIMARY KEY,
    user_order_uuid varchar(36) NOT NULL,
    name varchar(255) NOT NULL,
    amount integer NOT NULL,
    price decimal(15, 5) NOT NULL
);
