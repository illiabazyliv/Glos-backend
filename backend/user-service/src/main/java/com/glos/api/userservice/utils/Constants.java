package com.glos.api.userservice.utils;

import com.glos.api.entities.Role;

public class Constants {
    public static final String FRIENDS_GROUP_NAME = "friends";
    public static final Long DEFAULT_ACCESS_TYPE_ID_FOR_FRIENDS = 3L;

    public static final Role ROLE_ADMIN = new Role(1L, "ROLE_ADMIN");
    public static final Role ROLE_USER = new Role(2L, "ROLE_USER");
}
