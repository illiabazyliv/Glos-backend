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
    public static final String CLAIM_ROOT_FULL_NAME = "rootFullName";
    public static final String CLAIM_CODE = "code";
    public static final String DEFAULT_CODE_SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int DEFAULT_CODE_LENGTH = 6;

    private Constants() {}
}
