CREATE TABLE users(
    user_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE roles (
    role_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(100) NOT NULL
)ENGINE=InnoDB;

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
    -- UNIQUE (user_id, role_id)               -- уникальное значение

) ENGINE =InnoDB;

INSERT INTO users VALUES
(1, 'user', 'user'),
(2, 'admin', 'admin');

INSERT INTO roles VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES
(1, 1),
(2, 1),
(2, 2);