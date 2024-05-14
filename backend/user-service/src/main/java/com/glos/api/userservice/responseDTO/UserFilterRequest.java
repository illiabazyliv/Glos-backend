package com.glos.api.userservice.responseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserFilterRequest {
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String gender;

    private String firstName;

    private String lastName;

    private LocalDateTime birthdate;

    private Boolean isEnabled;

    private Boolean isDeleted;
    private List<String> roles;
    private List<GroupDTO> groups;

    private int page;
    private int size;
    private String sort;

    public UserFilterRequest() {
        this.roles = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public UserFilterRequest(Long id, String username, String password, String email, String phoneNumber, String gender, String firstName, String lastName, LocalDateTime birthdate, Boolean isEnabled, Boolean isDeleted, List<String> roles, List<GroupDTO> groups, int page, int size, String sort) {
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
        this.groups = groups;
        this.page = page;
        this.size = size;
        this.sort = sort;
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

    public List<GroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
