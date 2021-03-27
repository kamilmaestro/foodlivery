CREATE TABLE food (
    id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL,
    supplier_id bigint NOT NULL,
    price decimal(15, 5) NOT NULL,
    image_id bigint
);
