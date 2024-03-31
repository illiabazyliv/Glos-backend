package com.entities.application.entity;


import jakarta.persistence.*;

@Entity
@Table(
        name = "access_types",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "`name`", name = "uq_access_types_name")
        }

)

public class AccessType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "`name`", nullable = false, length = 20)
    private String name;
}
