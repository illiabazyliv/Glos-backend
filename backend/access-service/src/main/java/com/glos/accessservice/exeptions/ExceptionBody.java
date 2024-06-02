package com.glos.accessservice.exeptions;


public interface ExceptionBody {
    void setMessage(String message);
    void appendError(String key, String value);
    void removeError(String key);
}
