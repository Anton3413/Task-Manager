START TRANSACTION;

CREATE TABLE  IF NOT EXISTS users(
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(60) UNIQUE NOT NULL,
                                     password VARCHAR(300) NOT NULL,
                                     email VARCHAR UNIQUE NOT NULL,
                                     created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS task(
                                   id BIGSERIAL PRIMARY KEY,
                                   user_id BIGINT REFERENCES users(id),
                                   title VARCHAR(100) NOT NULL,
                                   description VARCHAR(2000),
                                   status VARCHAR NOT NULL,
                                   created_at TIMESTAMP NOT NULL,
                                   due_date TIMESTAMP
);

COMMIT;