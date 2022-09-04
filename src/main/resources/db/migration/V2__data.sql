INSERT INTO users(id, email, password)
values ('1122', 'email', 'password');

INSERT INTO authorities(id, name)
values (1, 'ADMIN');

INSERT INTO users_authorities(user_id, authority_id)
values (1122, 1);

INSERT INTO companies
VALUES (1, 'google', 'google@gmail.com', 'not provided');


INSERT INTO companies(id, name, email, website)
VALUES (2, 'yandex', 'yandex@yandex.ru', 'not provided');

INSERT INTO companies(id, name, email, website)
VALUES (3, 'youtube', 'youtube@gmail.com', 'not provided');

INSERT INTO employees(id, companyid, name, surname, phone_number, email)
VALUES (001, 1, 'John', 'Doe', 'not provided',
        'JohnDoe@gmail.com');
INSERT INTO employees(id, companyid, name, surname, phone_number, email)
VALUES (002, 2, 'John', 'White', '+79001234567',
        'JohnWhite@gmail.com');
INSERT INTO employees(id, companyid, name, surname, phone_number, email)
VALUES (003, 3, 'Jane', 'Smith', '+79001234568',
        'JohnSmith@gmail.com');
