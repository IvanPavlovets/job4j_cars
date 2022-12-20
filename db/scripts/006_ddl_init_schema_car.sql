create table if not exists car (
    id serial primary key,
    name VARCHAR NOT NULL,
    engine_id int not null references engine(id)
);