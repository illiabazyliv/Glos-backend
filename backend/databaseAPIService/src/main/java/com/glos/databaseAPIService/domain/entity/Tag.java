package com.glos.databaseAPIService.domain.entity;

import jakarta.persistence.*;


@Entity
@Table(
        name = "tags",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "uq_tags_name")}
)
public class Tag
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

}
