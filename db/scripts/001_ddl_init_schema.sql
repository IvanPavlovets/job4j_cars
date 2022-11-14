CREATE TABLE if not exists auto_user (
  id SERIAL PRIMARY KEY,
  login VARCHAR NOT NULL UNIQUE,
  password VARCHAR NOT NULL UNIQUE
);

CREATE TABLE if not exists auto_post (
  id SERIAL PRIMARY KEY,
  text TEXT,
  created TIMESTAMP,
  auto_user_id INT NOT NULL REFERENCES auto_user(id)
);