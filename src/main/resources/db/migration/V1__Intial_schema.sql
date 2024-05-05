CREATE TABLE book
(
    id BIGSERIAL PRIMARY KEY,
    author             VARCHAR(255) NOT NULL,
    isbn               VARCHAR(255) UNIQUE NOT NULL,
    price              DOUBLE PRECISION NOT NULL,
    title              VARCHAR(255) NOT NULL,
    created_date       TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL,
    version            INTEGER NOT NULL
);