package com.glos.commentservice.domain.exceptions;


public interface ExceptionBody {
    void setMessage(String message);
    void appendError(String key, String value);
    void removeError(String key);
}
