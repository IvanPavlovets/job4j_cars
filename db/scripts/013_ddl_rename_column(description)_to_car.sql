ALTER TABLE car RENAME COLUMN name to description;
ALTER TABLE car ADD COLUMN photo bytea;
ALTER TABLE car ADD COLUMN model_id int REFERENCES model(id);
ALTER TABLE car ADD COLUMN carbody_id int REFERENCES carbody(id);