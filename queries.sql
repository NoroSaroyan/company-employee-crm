CREATE TABLE companies
(
    id      BIGINT NOT NULL PRIMARY KEY,
    name    TEXT   NOT NULL UNIQUE,
    email   TEXT UNIQUE,
    website TEXT
);
CREATE TABLE employees
(
    id           BIGINT NOT NULL PRIMARY KEY,
    companyId    BIGINT REFERENCES companies (id),
    name         TEXT   NOT NULL,
    surname      TEXT   NOT NULL,
    phone_number TEXT UNIQUE,
    email        TEXT UNIQUE
);
create table users
(
    id       BIGINT NOT NULL PRIMARY KEY,
    email    TEXT   NOT NULL,
    password TEXT   NOT NULL
);

CREATE TABLE authorities
(
    id   BIGINT UNIQUE PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE users_authorities
(
    user_id BIGINT,
    authority_id BIGINT,
    CONSTRAINT userId_roleId PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (authority_id) REFERENCES authorities (id)
);
DROP Table users_authorities;

INSERT INTO users(id, email, password)
values ('1122', 'email', 'password');

INSERT INTO authorities(id, name)
values (1, 'ADMIN');

INSERT INTO users_authorities(user_id, authority_id)
values (1122, 1);

INSERT INTO companies
VALUES ( 1, 'google', 'google@gmail.com', 'not provided');

INSERT INTO employees
VALUES (001, 1,'John', 'Doe', 'not provided',
        'JohnDoe@gmail.com');
