CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    age INT
    );

CREATE TABLE IF NOT EXISTS employer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS account (
    id SERIAL  PRIMARY KEY,
    number VARCHAR(255),
    currency VARCHAR(255),
    balance DOUBLE,
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
    );

CREATE TABLE IF NOT EXISTS customer_employer (
    customer_id INT,
    employer_id INT,
    PRIMARY KEY (customer_id, employer_id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (employer_id) REFERENCES employer(id)
    );

