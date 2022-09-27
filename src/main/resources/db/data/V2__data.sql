INSERT INTO users(email, password)
values ('admin@test.com', 'password');

INSERT INTO authorities(name)
values ('ADMIN');

INSERT INTO users_authorities(user_id, authority_id)
values (1, 1);

INSERT INTO companies(name, email, website)
VALUES ('google', 'google@gmail.com', 'not provided');

INSERT INTO companies(name, email, website)
VALUES ('yandex', 'yandex@yandex.ru', 'not provided');

INSERT INTO companies(name, email, website)
VALUES ('youtube', 'youtube@gmail.com', 'not provided');

INSERT INTO employees(id, company_id, name, surname, phone_number, email)
VALUES (1, 1, 'John', 'Doe', 'not provided',
        'JohnDoe@gmail.com');
INSERT INTO employees(id, company_id, name, surname, phone_number, email)
VALUES (2, 2, 'John', 'White', '+79001234567',
        'JohnWhite@gmail.com');
INSERT INTO employees(id, company_id, name, surname, phone_number, email)
VALUES (3, 3, 'Jane', 'Smith', '+79001234568',
        'JohnSmith@gmail.com');
