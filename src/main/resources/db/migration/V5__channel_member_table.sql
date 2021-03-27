CREATE TABLE channel_members (
    id bigserial PRIMARY KEY,
    channel_id bigint NOT NULL,
    member_id bigint NOT NULL,
    member_name varchar(255) NOT NULL,
    joined_at timestamp with time zone NOT NULL
);
