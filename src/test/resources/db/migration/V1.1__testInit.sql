CREATE TABLE companies
(
    id      LONG AUTO_INCREMENT PRIMARY KEY,
    name    TEXT NOT NULL,
    email   TEXT,
    website TEXT
);
CREATE TABLE employees
(
    id           LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_id   BIGINT REFERENCES companies (id),
    name         TEXT NOT NULL,
    surname      TEXT NOT NULL,
    phone_number TEXT,
    email        TEXT
);
create table users
(
    id       LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE authorities
(
    id   LONG        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE users_authorities
(
    user_id      LONG,
    authority_id LONG,
    CONSTRAINT userId_roleId PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (authority_id) REFERENCES authorities (id)
);
