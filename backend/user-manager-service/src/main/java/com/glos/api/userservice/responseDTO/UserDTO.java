package com.glos.api.userservice.responseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glos.api.entities.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDTO
{
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String gender;

    private String firstName;

    private String lastName;

    private LocalDateTime birthdate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY )
    private Boolean isEnabled = true;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY )
    private Boolean isDeleted = false;

    private List<Role> roles;

    public UserDTO() {
        this.roles = new ArrayList<>();
    }

    public UserDTO(Long id, String username, String password, String email, String phoneNumber, String gender, String firstName, String lastName, LocalDateTime birthdate, Boolean isEnabled, Boolean isDeleted, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.isEnabled = isEnabled;
        this.isDeleted = isDeleted;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
