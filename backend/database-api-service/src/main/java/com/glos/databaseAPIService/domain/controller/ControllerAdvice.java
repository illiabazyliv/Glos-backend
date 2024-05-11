package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.exceptions.InternalExceptionBody;
import com.glos.databaseAPIService.domain.exceptions.ResourceAlreadyExistsException;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.lang.reflect.InvocationTargetException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {
            EntityNotFoundException.class,
            ResourceNotFoundException.class,
            NoResourceFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public InternalExceptionBody handleResourceNotFound(
            Exception e
    ) {
        return new InternalExceptionBody(e.getClass(), e.getMessage());
    }

    @ExceptionHandler({
            ResourceAlreadyExistsException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            InvocationTargetException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InternalExceptionBody handleBadRequest(
            Exception e
    ) {
        return new InternalExceptionBody(e.getClass(), e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InternalExceptionBody handleDataIntegrityViolationException(
            DataIntegrityViolationException e
    ) {
        return new InternalExceptionBody(e.getClass(), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InternalExceptionBody handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation error: ");
        ex.getConstraintViolations().forEach(violation -> {
            errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
        });
        return new InternalExceptionBody(ex.getClass(), errorMessage.toString());
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InternalExceptionBody handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        if (ex.getCause() instanceof org.hibernate.TransientPropertyValueException) {
            org.hibernate.TransientPropertyValueException nestedException = (org.hibernate.TransientPropertyValueException) ex.getCause();
            String propertyName = nestedException.getPropertyName();
            String message = String.format("Transient property value exception: %s", propertyName);
            return new InternalExceptionBody(ex.getClass(), message);
        }
        return new InternalExceptionBody(ex.getClass(), "Invalid data access: " + ex.getMessage());
    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public InternalExceptionBody handleOptimisticLockException(
            OptimisticLockException e
    ){
        return new InternalExceptionBody(e.getClass(), e.getMessage());
    }

    @ExceptionHandler({
            DataAccessException.class,
            JpaSystemException.class,
            PersistenceException.class,
            Throwable.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public InternalExceptionBody handleResourceNotFound(Throwable throwable) {
        return new InternalExceptionBody(throwable.getClass(), throwable.getMessage());
    }
}
