create table if not exists brand (
    id serial primary key,
    name VARCHAR NOT NULL unique
);