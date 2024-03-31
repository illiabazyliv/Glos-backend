package com.entities.application.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username", name = "uq_users_username"),
                @UniqueConstraint(columnNames = "email", name = "uq_users_email"),
                @UniqueConstraint(columnNames = "phone_number", name = "uq_users_phone_number")
        }
)
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "username", nullable = false, length = 40, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 72)
    private String password_hash;

    @Column(name = "email", nullable = false, length = 320, unique = true)
    private String email;

    @Column(name = "phone_number", length = 15, unique = true)
    private String phone_number;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "first_name", length = 50)
    private String first_name;

    @Column(name = "last_name", length = 50)
    private String last_name;

    @Column(name = "birthdate")
    private LocalDateTime birthdate;

    @Column(name = "is_account_non_expired", nullable = false, columnDefinition="boolean default true")
    private boolean is_account_non_expired;

    @Column(name = "is_account_non_locked", nullable = false, columnDefinition = "boolean default true")
    private boolean is_account_non_locked;

    @Column(name = "is_credentials_non_expired", nullable = false, columnDefinition = "boolean default true")
    private boolean is_credentials_non_expired;

    @Column(name = "is_enabled", nullable = false, columnDefinition = "boolean default true")
    private boolean is_enabled;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default true")
    private boolean is_deleted;

    @OneToMany(mappedBy = "owner")
    private List<Group> groups;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_users_roles_users_id"),
            inverseForeignKey = @ForeignKey(name = "fk_users_roles_roles_id"))
    private List<Role> roles;


}
