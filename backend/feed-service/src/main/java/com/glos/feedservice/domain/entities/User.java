package com.glos.feedservice.domain.entities;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isIs_account_non_expired() {
        return is_account_non_expired;
    }

    public void setIs_account_non_expired(boolean is_account_non_expired) {
        this.is_account_non_expired = is_account_non_expired;
    }

    public boolean isIs_account_non_locked() {
        return is_account_non_locked;
    }

    public void setIs_account_non_locked(boolean is_account_non_locked) {
        this.is_account_non_locked = is_account_non_locked;
    }

    public boolean isIs_credentials_non_expired() {
        return is_credentials_non_expired;
    }

    public void setIs_credentials_non_expired(boolean is_credentials_non_expired) {
        this.is_credentials_non_expired = is_credentials_non_expired;
    }

    public boolean isIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User(Long id,
                String username,
                String password_hash,
                String email,
                String phone_number,
                String gender,
                String first_name,
                String last_name,
                LocalDateTime birthdate,
                boolean is_account_non_expired,
                boolean is_account_non_locked,
                boolean is_credentials_non_expired,
                boolean is_enabled,
                boolean is_deleted,
                List<Group> groups,
                List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthdate = birthdate;
        this.is_account_non_expired = is_account_non_expired;
        this.is_account_non_locked = is_account_non_locked;
        this.is_credentials_non_expired = is_credentials_non_expired;
        this.is_enabled = is_enabled;
        this.is_deleted = is_deleted;
        this.groups = groups;
        this.roles = roles;
    }
}
