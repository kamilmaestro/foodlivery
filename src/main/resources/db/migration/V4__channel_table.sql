CREATE TABLE channels (
    id bigserial PRIMARY KEY,
    name varchar(100) NOT NULL,
    uuid varchar(36) NOT NULL,
    created_by bigint NOT NULL
);
