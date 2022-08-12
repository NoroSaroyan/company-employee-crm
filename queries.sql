CREATE TABLE companies
(
    id      LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    TEXT NOT NULL UNIQUE,
    email   TEXT UNIQUE,
    website TEXT,
);
CREATE TABLE employees
(
    id           LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    companyId    LONG REFERENCES companies (id),
    name         TEXT NOT NULL,
    surname      TEXT NOT NULL,
    phone_number TEXT UNIQUE,
    email        TEXT UNIQUE,
);
create table users
(
    id       LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE roles
(
    name TEXT UNIQUE NOT NULL PRIMARY KEY
);

CREATE TABLE users_roles_relations
(
    userId   LONG REFERENCES users (id),
    roleName TEXT REFERENCES roles (name),
    CONSTRAINT userId_roleName PRIMARY KEY (userId, roleName)
);

INSERT INTO roles
VALUES('ADMIN');

INSERT INTO companies
VALUES (id = 1, name = 'google', email = 'google@gmail.com', 'not provided');

INSERT INTO employees
VALUES (id = 001, companyId = 1, name = 'John', surname = 'Doe', phone_number = 'not provided',
        email = 'JohnDoe@gmail.com');
