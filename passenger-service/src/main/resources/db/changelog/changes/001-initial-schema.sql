CREATE TABLE IF NOT EXISTS PASSENGERS(
id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS identity,
name VARCHAR(32) NOT NULL,
last_name VARCHAR(32) NOT NULL,
email VARCHAR(255) NOT NULL,
phone_number VARCHAR(13) NOT NULL,
is_deleted BOOLEAN NOT NULL
);