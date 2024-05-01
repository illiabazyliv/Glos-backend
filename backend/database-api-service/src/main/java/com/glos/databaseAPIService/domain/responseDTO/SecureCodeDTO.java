package com.glos.databaseAPIService.domain.responseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class SecureCodeDTO
{
    private Long id;

    private String code;

    private LocalDateTime creationDate;

    private LocalDateTime expirationDate;

    public SecureCodeDTO(Long id, String code, LocalDateTime creationDate, LocalDateTime expirationDate) {
        this.id = id;
        this.code = code;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public SecureCodeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
