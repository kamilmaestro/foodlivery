CREATE TABLE images (
    id bigserial PRIMARY KEY,
    file_name varchar(255) NOT NULL,
    file_type varchar(255) NOT NULL,
    data oid NOT NULL
);
