START TRANSACTION;

CREATE TABLE  IF NOT EXISTS users(
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(50) UNIQUE NOT NULL,
                                     email VARCHAR UNIQUE NOT NULL,
                                     created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS task(
                                   id BIGSERIAL PRIMARY KEY,
                                   user_id BIGINT REFERENCES users(id),
                                   title VARCHAR(150) UNIQUE NOT NULL,
                                   description VARCHAR(500),
                                   status VARCHAR NOT NULL,
                                   created_at TIMESTAMP NOT NULL UNIQUE,
                                   due_date TIMESTAMP
);

COMMIT;
