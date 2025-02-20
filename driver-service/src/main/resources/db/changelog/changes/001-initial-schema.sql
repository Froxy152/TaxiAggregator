create table if not exists drivers(
    id bigint primary key generated by default as identity,
    name varchar(32) not null,
    last_name varchar(32) not null,
    email varchar(255) not null unique ,
    phone_number varchar(13) not null unique,
    rating float not null default 0,
    gender int not null,
    is_deleted boolean default false
);

create table if not exists cars(
    id bigint primary key generated by default as identity,
    car_brand varchar(32) not null,
    car_number varchar(10) not null unique,
    car_color varchar(32) not null,
    driver_id bigint references drivers(id),
    is_deleted boolean default false
)