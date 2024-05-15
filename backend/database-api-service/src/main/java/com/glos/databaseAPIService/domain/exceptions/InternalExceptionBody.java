package com.glos.databaseAPIService.domain.exceptions;

public record InternalExceptionBody(
        Class<? extends Throwable> exceptionType,
        String message
) { }
