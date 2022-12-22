create table if not exists carbody (
    id serial primary key,
    name VARCHAR NOT NULL unique
);