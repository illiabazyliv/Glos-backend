package com.glos.accessservice.utils;

public final class Constants {

    public static final int DEFAULT_EXPIRED_SECONDS = 60 * 5;
    public static final String DEFAULT_CODE_SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int DEFAULT_CODE_LENGTH = 6;

    public static final Long DEFAULT_EXPIRED_MILLIS = (long) (DEFAULT_EXPIRED_SECONDS*10);


    private Constants() {}
}