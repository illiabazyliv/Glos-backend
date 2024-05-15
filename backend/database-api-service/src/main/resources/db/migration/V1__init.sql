CREATE DATABASE IF NOT EXISTS glos_database;
USE glos_database;

CREATE TABLE IF NOT EXISTS access_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
     PRIMARY KEY(id),
     CONSTRAINT uq_access_types_name UNIQUE(`name`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    CONSTRAINT pk_roles_id PRIMARY KEY (id),
    CONSTRAINT uq_roles_name UNIQUE(`name`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tags (
    id BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    CONSTRAINT pk_tags_id PRIMARY KEY(id),
    CONSTRAINT uq_tags_name UNIQUE(`name`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS secure_codes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(255) NOT NULL,
    creation_date DATETIME NOT NULL,
    expiration_date DATETIME NOT NULL,
    CONSTRAINT pk_secure_codes_id PRIMARY KEY(id),
    CONSTRAINT uq_secure_codes_code UNIQUE(`code`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    gender NVARCHAR(255),
    first_name NVARCHAR(100),
    last_name NVARCHAR(100),
    birthdate DATETIME,
    is_account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    is_account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    is_credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    is_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_datetime DATETIME,
    blocked_datetime DATETIME,
    disabled_datetime DATETIME,
    created_datetime DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_datetime DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_users_id PRIMARY KEY(id),
    CONSTRAINT uq_users_username UNIQUE(username),
    CONSTRAINT uq_users_email UNIQUE(email),
    CONSTRAINT uq_users_phone_number UNIQUE(phone_number)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `groups` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    owner_id BIGINT NOT NULL,
    CONSTRAINT pk_groups_id PRIMARY KEY(id),
    CONSTRAINT fk_groups_users_id FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uq_groups_name_owner_id UNIQUE(`name`, owner_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS repositories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    root_path VARCHAR(255) NOT NULL,
    root_name VARCHAR(255) NOT NULL,
    root_full_name VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    is_default BOOLEAN DEFAULT NULL,
    display_path NVARCHAR(255),
    display_name NVARCHAR(255),
    display_full_name NVARCHAR(255),
    description NVARCHAR(500),
    CONSTRAINT pk_repositories_id PRIMARY KEY(id),
    CONSTRAINT uq_repositories_owner_id_root_full_name UNIQUE(owner_id, root_full_name),
    CONSTRAINT uq_repositories_owner_id_is_default UNIQUE(owner_id, is_default),
    CONSTRAINT fk_repositories_users_id FOREIGN KEY (owner_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    author_id BIGINT,
    `text` TEXT NOT NULL,
    `date` DATETIME NOT NULL,
    CONSTRAINT pk_comments_id PRIMARY KEY(id),
    CONSTRAINT fk_comments_users_id FOREIGN KEY (author_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users_roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_users_roles_id PRIMARY KEY(id),
    CONSTRAINT fk_users_roles_users_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_roles_roles_id FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS groups_users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_groups_users_id PRIMARY KEY(id),
    CONSTRAINT fk_groups_users_groups_id FOREIGN KEY (group_id) REFERENCES `groups`(id) ON DELETE CASCADE,
    CONSTRAINT fk_groups_users_users_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS groups_access_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    group_id BIGINT NOT NULL,
    access_type_id BIGINT NOT NULL,
    CONSTRAINT pk_groups_access_types_id PRIMARY KEY(id),
    CONSTRAINT fk_groups_access_types_groups_id FOREIGN KEY (group_id) REFERENCES `groups`(id) ON DELETE CASCADE,
    CONSTRAINT fk_groups_access_types_access_types_id FOREIGN KEY (access_type_id) REFERENCES access_types(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS files (
    id BIGINT NOT NULL AUTO_INCREMENT,
    root_path VARCHAR(255) NOT NULL,
    root_filename VARCHAR(255) NOT NULL,
    root_full_name VARCHAR(255) NOT NULL,
    root_size INT NOT NULL,
    root_format VARCHAR(10) NOT NULL,
    display_path NVARCHAR(255),
    display_filename NVARCHAR(255),
    display_full_name NVARCHAR(255),
    repository_id BIGINT,
    CONSTRAINT pk_files_id PRIMARY KEY(id),
    CONSTRAINT fk_files_repositories_id FOREIGN KEY (repository_id) REFERENCES repositories(id),
    CONSTRAINT uq_files_repository_id_root_full_name UNIQUE(repository_id, root_full_name),
    CONSTRAINT ch_root_size_is_not_negative CHECK(root_size >= 0)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS files_access_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    file_id BIGINT NOT NULL,
    access_type_id BIGINT NOT NULL,
    CONSTRAINT pk_files_access_types_id PRIMARY KEY(id),
    CONSTRAINT fk_files_access_types_files_id FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE,
    CONSTRAINT fk_files_access_types_access_types_id FOREIGN KEY (access_type_id) REFERENCES access_types(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS files_user_access_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    file_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    access_type_id BIGINT NOT NULL,
    CONSTRAINT pk_files_user_access_types_id PRIMARY KEY(id),
    CONSTRAINT fk_files_user_access_types_files_id FOREIGN KEY (file_id) REFERENCES files(id),
    CONSTRAINT fk_files_user_access_types_users_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_files_user_access_types_access_types_id FOREIGN KEY (access_type_id) REFERENCES access_types(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS files_secure_codes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    file_id BIGINT NOT NULL,
    secure_code_id BIGINT NOT NULL,
    CONSTRAINT pk_files_secure_codes_id PRIMARY KEY(id),
    CONSTRAINT fk_files_secure_codes_files_id FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE,
    CONSTRAINT fk_files_secure_codes_secure_codes_id FOREIGN KEY (secure_code_id) REFERENCES secure_codes(id) ON DELETE CASCADE,
    CONSTRAINT uq_files_secure_codes_file_id_secure_code_id UNIQUE(file_id, secure_code_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS repositories_access_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    repository_id BIGINT NOT NULL,
    access_type_id BIGINT NOT NULL,
    CONSTRAINT pk_repositories_access_types_id PRIMARY KEY(id),
    CONSTRAINT fk_repositories_access_types_repositories_id FOREIGN KEY (repository_id) REFERENCES repositories(id) ON DELETE CASCADE,
    CONSTRAINT fk_repositories_access_types_access_types_id FOREIGN KEY (access_type_id) REFERENCES access_types(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS repositories_users_access_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    repository_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    access_type_id BIGINT NOT NULL,
    CONSTRAINT pk_repositories_users_access_types_id PRIMARY KEY(id),
    CONSTRAINT fk_repositories_users_access_types_repositories_id FOREIGN KEY (repository_id) REFERENCES repositories(id),
    CONSTRAINT fk_repositories_users_access_types_users_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_repositories_users_access_types_access_types_id FOREIGN KEY (access_type_id) REFERENCES access_types(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS repositories_secure_codes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    repository_id BIGINT NOT NULL,
    secure_code_id BIGINT NOT NULL,
    CONSTRAINT pk_repositories_secure_codes_id PRIMARY KEY(id),
    CONSTRAINT fk_repositories_secure_codes_repositories_id FOREIGN KEY (repository_id) REFERENCES repositories(id) ON DELETE CASCADE,
    CONSTRAINT fk_repositories_secure_codes_secure_codes_id FOREIGN KEY (secure_code_id) REFERENCES secure_codes(id) ON DELETE CASCADE,
    CONSTRAINT uq_repositories_secure_codes_repository_id_secure_code_id UNIQUE(repository_id, secure_code_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS files_tags (
    id BIGINT NOT NULL AUTO_INCREMENT,
    file_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    CONSTRAINT pk_files_tags_id PRIMARY KEY(id),
    CONSTRAINT fk_files_tags_files_id FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE,
    CONSTRAINT fk_files_tags_tags_id FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS repositories_tags (
    id BIGINT NOT NULL AUTO_INCREMENT,
    repository_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    CONSTRAINT pk_repositories_tags_id PRIMARY KEY(id),
    CONSTRAINT fk_repositories_tags_repositories_id FOREIGN KEY (repository_id) REFERENCES repositories(id) ON DELETE CASCADE,
    CONSTRAINT fk_repositories_tags_tags_id FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS files_comments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    file_id BIGINT NOT NULL,
    comment_id BIGINT NOT NULL,
    CONSTRAINT pk_files_comments_id PRIMARY KEY(id),
    CONSTRAINT fk_files_comments_files_id FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE,
    CONSTRAINT fk_files_comments_comments_id FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS repositories_comments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    repository_id BIGINT NOT NULL,
    comment_id BIGINT NOT NULL,
    CONSTRAINT pk_repositories_comments_id PRIMARY KEY(id),
    CONSTRAINT fk_repositories_comments_repositories_id FOREIGN KEY (repository_id) REFERENCES repositories(id) ON DELETE CASCADE,
    CONSTRAINT fk_repositories_comments_comments_id FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
) ENGINE=InnoDB;

DELIMITER $$
CREATE TRIGGER IF NOT EXISTS after_insert_users
    AFTER INSERT ON users
    FOR EACH ROW
BEGIN
    INSERT INTO `groups`(name, owner_id)
    VALUES ('friends', NEW.id);
END$$
DELIMITER ;