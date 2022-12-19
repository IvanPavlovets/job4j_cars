create table if not exists engine (
    id serial primary key,
    name VARCHAR NOT NULL
);

create table if not exists car (
    id serial primary key,
    name VARCHAR NOT NULL,
    engine_id int not null references engine(id)
);

create table if not exists driver (
    id serial primary key,
    name VARCHAR NOT NULL
);

create table if not exists history_owner (
    id serial primary key,
    driver_id int not null references driver(id),
    car_id int not null references car(id)
);


ALTER TABLE auto_post ADD COLUMN car_id int REFERENCES car(id);
