CREATE TABLE companies
(
    id      BIGINT NOT NULL PRIMARY KEY,
    name    TEXT   NOT NULL UNIQUE,
    email   TEXT   UNIQUE ,
    website TEXT
);
CREATE TABLE employees
(
    id           BIGINT NOT NULL PRIMARY KEY,
    companyId    BIGINT REFERENCES companies (id),
    name         TEXT   NOT NULL,
    surname      TEXT   NOT NULL,
    phone_number TEXT   NOT NULL UNIQUE,
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
    user_id      BIGINT,
    authority_id BIGINT,
    CONSTRAINT userId_roleId PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (authority_id) REFERENCES authorities (id)
);
