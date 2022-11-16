create table if not exists participates (
    id serial primary key,
    user_id INT NOT NULL REFERENCES auto_user(id),
    post_id INT NOT NULL REFERENCES auto_post(id)
);