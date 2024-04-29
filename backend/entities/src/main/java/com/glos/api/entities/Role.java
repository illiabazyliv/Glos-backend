package com.glos.api.entities;

import jakarta.persistence.*;

@Entity
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uq_roles_name")
        }
)
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",  nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
