CREATE TABLE companies
(
    id      BIGSERIAL NOT NULL PRIMARY KEY,
    name    TEXT   NOT NULL UNIQUE,
    email   TEXT   UNIQUE ,
    website TEXT,
    path TEXT
);
CREATE TABLE employees
(
    id           BIGSERIAL NOT NULL PRIMARY KEY,
    company_id   BIGINT REFERENCES companies (id),
    name         TEXT      NOT NULL,
    surname      TEXT      NOT NULL,
    phone_number TEXT      NOT NULL UNIQUE,
    email        TEXT UNIQUE
);
create table users
(
    id       BIGSERIAL NOT NULL PRIMARY KEY,
    email    TEXT      NOT NULL,
    password TEXT      NOT NULL
);

CREATE TABLE authorities
(
    id   BIGSERIAL UNIQUE PRIMARY KEY,
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
