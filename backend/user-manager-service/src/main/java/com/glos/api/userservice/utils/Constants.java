package com.glos.api.userservice.utils;

import com.glos.api.userservice.entities.Role;

import java.time.Duration;

public class Constants {
    public static final String FRIENDS_GROUP_NAME = "friends";
    public static final Long DEFAULT_ACCESS_TYPE_ID_FOR_FRIENDS = 3L;
    public static final Duration DURATION_DELETED_STATE = Duration.ofDays(5);

    public static final Role ROLE_ADMIN = new Role(1L, "ROLE_ADMIN");
    public static final Role ROLE_USER = new Role(2L, "ROLE_USER");
    public static final boolean DEFAULT_IS_ACCOUNT_NON_EXPIRED = true;
    public static final boolean DEFAULT_IS_ACCOUNT_NON_LOCKED = true;
    public static final boolean DEFAULT_IS_CREDENTIALS_NON_EXPIRED = true;
    public static final boolean DEFAULT_IS_ENABLED = false;
    public static final boolean DEFAULT_IS_DELETED = false;
}
