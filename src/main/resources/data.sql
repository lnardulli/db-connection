DROP TABLE IF EXISTS animal;

CREATE TABLE animal (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);
INSERT INTO animal (name) VALUES
  ('Dog'),
  ('Cat'),
  ('Rabbit');