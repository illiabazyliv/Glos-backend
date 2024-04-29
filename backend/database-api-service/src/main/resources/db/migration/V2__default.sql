USE glos_database;

INSERT INTO access_types(id, name)
VALUES
(1, 'PRIVATE_RW'),
(2, 'PROTECTED_R'),
(3, 'PROTECTED_RW'),
(4, 'PUBLIC_R'),
(5, 'PUBLIC_RW');

INSERT INTO roles(id, name)
VALUES (1, 'ROLE_USER');