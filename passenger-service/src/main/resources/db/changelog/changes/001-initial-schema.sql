create table if not exists passengers(
id BIGINT primary key generated by default as identity,
name varchar(32) not null,
last_name varchar(32) not null,
email varchar(255) not null,
phone_number varchar(13) not null,
is_deleted boolean not null
);