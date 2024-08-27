
INSERT INTO customer (name, email, age) VALUES ('Goga Gogishvilly', 'gogy@example.com', 30);
INSERT INTO customer (name, email, age) VALUES ('Petya Petrenko', 'petro@example.com', 25);
INSERT INTO customer (name, email, age) VALUES ('Alice Johnson', 'alice.johnson@example.com', 35);

INSERT INTO employer (name, address) VALUES ('TechCorp', '123 Tech Street');
INSERT INTO employer (name, address) VALUES ('BizInc', '456 Biz Avenue');
INSERT INTO employer (name, address) VALUES ('Finance LLC', '789 Finance Blvd');

INSERT INTO account (number, currency, balance, customer_id) VALUES ('1234567890', 'USD', 1000.0, 1);
INSERT INTO account (number, currency, balance, customer_id) VALUES ('0987654321', 'EUR', 2000.0, 2);
INSERT INTO account (number, currency, balance, customer_id) VALUES ('3456789012', 'UAH', 3000.0, 2);
INSERT INTO account (number, currency, balance, customer_id) VALUES ('4567890123', 'CHF', 4000.0, 3);
INSERT INTO account (number, currency, balance, customer_id) VALUES ('5678901234', 'GBP', 5000.0, 3);

INSERT INTO customer_employer (customer_id, employer_id) VALUES (1, 1);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (1, 2);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (2, 3);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (3, 1);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (3, 3);


