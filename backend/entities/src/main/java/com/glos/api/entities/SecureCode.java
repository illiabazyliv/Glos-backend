package com.glos.api.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(
        name = "secure_codes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code", name = "uq_secure_codes_code"),
                @UniqueConstraint(columnNames = {"resource_path"}, name = "uq_secure_codes_receiver_resource_path")
        }
)
public class SecureCode
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", length = 255, nullable = false, unique = true)
    private String code;

    @Column(name = "receiver", length = 255, nullable = false)
    private String receiver;

    @Column(name = "resource_path", length = 255, nullable = false)
    private String resourcePath;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    public SecureCode(Long id, String code, String receiver, String resourcePath, LocalDateTime creationDate, LocalDateTime expirationDate) {
        this.id = id;
        this.code = code;
        this.receiver = receiver;
        this.resourcePath = resourcePath;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public SecureCode() {
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


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecureCode that = (SecureCode) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(receiver, that.receiver) && Objects.equals(resourcePath, that.resourcePath) && Objects.equals(creationDate, that.creationDate) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, receiver, resourcePath, creationDate, expirationDate);
    }
}
