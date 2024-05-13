package com.glos.api.userservice.responseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glos.api.entities.Role;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO
{
    private Long id;

    private String username;

    private String password_hash;

    private String email;

    private String phone_number;

    private String gender;

    private String first_name;

    private String last_name;

    private LocalDateTime birthdate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY )
    private Boolean isEnabled = true;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY )
    private Boolean isDeleted = false;

    private List<RoleDTO> roles;

    public UserDTO(Long id,
                   String username,
                   String password_hash,
                   String email, String phone_number,
                   String gender, String first_name,
                   String last_name, LocalDateTime birthdate,
                   Boolean isEnabled, Boolean isDeleted,
                   List<RoleDTO> roles) {
        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthdate = birthdate;
        this.isEnabled = isEnabled;
        this.isDeleted = isDeleted;
        this.roles = roles;
    }

    public UserDTO() {
    }

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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
