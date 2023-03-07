CREATE TABLE if not exists price_history (
   id SERIAL PRIMARY KEY,
   before BIGINT not null,
   after BIGINT not null,
   created TIMESTAMP,
   auto_post_id INT REFERENCES auto_post(id)
);
