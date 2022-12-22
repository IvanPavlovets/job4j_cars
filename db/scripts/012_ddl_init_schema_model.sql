create table if not exists model (
    id serial primary key,
    name VARCHAR NOT NULL,
    brand_id int NOT NULL REFERENCES brand(id)
);