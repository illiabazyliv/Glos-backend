package com.glos.databaseAPIService.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(
        name = "secure_codes",
        uniqueConstraints = {@UniqueConstraint(columnNames = "code", name = "uq_secure_codes_code")}
)
public class SecureCode
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", length = 255, nullable = false, unique = true)
    private String code;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;
}
