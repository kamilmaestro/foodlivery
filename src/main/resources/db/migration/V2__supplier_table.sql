CREATE TABLE suppliers (
    id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL,
    phone_number varchar(22) NOT NULL,
    email varchar(255) NOT NULL,
    created_at timestamp with time zone NOT NULL
);
