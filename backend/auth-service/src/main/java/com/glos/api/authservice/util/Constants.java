package com.glos.api.authservice.util;

import com.glos.api.entities.Role;

public final class Constants {

    public static final Role ROLE_ADMIN = new Role(1L, "ROLE_ADMIN");
    public static final Role ROLE_USER = new Role(2L, "ROLE_USER");
    public static final boolean DEFAULT_IS_ACCOUNT_NON_EXPIRED = true;
    public static final boolean DEFAULT_IS_ACCOUNT_NON_LOCKED = true;
    public static final boolean DEFAULT_IS_CREDENTIALS_NON_EXPIRED = true;
    public static final boolean DEFAULT_IS_ENABLED = false;
    public static final boolean DEFAULT_IS_DELETED = false;

    private Constants() {}
}
