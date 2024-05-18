USE glos_database;

INSERT IGNORE INTO access_types(id, name)
VALUES
(1, 'PRIVATE_RW'),
(2, 'PROTECTED_R'),
(3, 'PROTECTED_RW'),
(4, 'PUBLIC_R'),
(5, 'PUBLIC_RW');

INSERT IGNORE INTO roles(id, name)
VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_SYS');

INSERT IGNORE INTO users(id, username, email, password_hash)
VALUES
(1, 'sys', 'global.storage.info@gmail.com', '$2a$12$J/qxhK3mIwYdAfHWGLEPEePWpOpMgB/zwFfWhW8BhFqbiQ4xo0Tbi');

INSERT IGNORE INTO users_roles(id, user_id, role_id)
VALUES
(1, 1, 3);