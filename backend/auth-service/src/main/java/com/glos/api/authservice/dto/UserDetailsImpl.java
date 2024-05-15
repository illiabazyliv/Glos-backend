package com.glos.api.authservice.dto;

import com.glos.api.entities.Group;
import com.glos.api.entities.User;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public void setUsername(String username) {
        user.setUsername(username);
    }

    @Override
    public String getPassword() {
        return user.getPassword_hash();
    }

    public void setPassword(String passwordHash) {
        user.setPassword_hash(passwordHash);
    }

    public String getEmail() {
        return user.getEmail();
    }

    public void setEmail(String email) {
        user.setEmail(email);
    }

    public String getPhoneNumber() {
        return user.getPhone_number();
    }

    public void setPhoneNumber(String phoneNumber) {
        user.setPhone_number(phoneNumber);
    }

    public String getGender() {
        return user.getGender();
    }

    public void setGender(String gender) {
        user.setGender(gender);
    }

    public String getFirstName() {
        return user.getFirst_name();
    }

    public void setFirstName(String firstName) {
        user.setFirst_name(firstName);
    }

    public String getLastName() {
        return user.getLast_name();
    }

    public void setLastName(String lastName) {
       user.setLast_name(lastName);
    }

    public LocalDate getBirthdate() {
        return user.getBirthdate();
    }

    public void setBirthdate(LocalDate birthdate) {
        user.setBirthdate(birthdate);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map( x -> new Role(x.getName())).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIs_account_non_expired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIs_account_non_locked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIs_credentials_non_expired();
    }

    @Override
    public boolean isEnabled() {
        return user.getIs_enabled();
    }
}
