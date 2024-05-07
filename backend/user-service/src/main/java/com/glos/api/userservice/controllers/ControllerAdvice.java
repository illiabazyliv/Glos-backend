package com.glos.api.userservice.controllers;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice
{
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> notFound(FeignException ex)
    {
        return ResponseEntity.status(ex.status()).build();
    }
}
