DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS patients;

CREATE TABLE doctors
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    patronymic     VARCHAR(255),
    specialization VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE patients
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    patronymic   varchar(255),
    phone_number VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE recipes
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
    creation_date DATE         NOT NULL,
    description   VARCHAR(255) NOT NULL,
    priority      VARCHAR(255) NOT NULL,
    validity      DATE         NOT NULL,
    doctors_id    BIGINT       NOT NULL,
    patients_id   BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (doctors_id) REFERENCES doctors ON DELETE RESTRICT,
    FOREIGN KEY (patients_id) REFERENCES patients ON DELETE RESTRICT
);

