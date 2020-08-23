DROP TABLE IF EXISTS client;

CREATE TABLE client (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  user_name VARCHAR(50) NOT NULL,
  email VARCHAR(50),
  address VARCHAR(255) NOT NULL,
  owner VARCHAR(50) NOT NULL,
  country_id VARCHAR(3) NOT NULL,
  FOREIGN KEY (country_id) REFERENCES country (id)
);
