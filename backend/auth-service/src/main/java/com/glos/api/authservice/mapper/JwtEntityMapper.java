package com.glos.api.authservice.mapper;

import com.glos.api.authservice.util.security.JwtEntity;
import com.glos.api.entities.Role;
import com.glos.api.entities.User;
import com.glos.api.entities.mappers.AbstractMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JwtEntityMapper extends AbstractMapper<User, JwtEntity> {
    @Override
    protected void postDtoCopy(User source, JwtEntity destination) {
        destination.setPassword(source.getPassword_hash());
        destination.setPhoneNumber(source.getPhone_number());
        if (source.getRoles() != null && !source.getRoles().isEmpty()) {
            destination.setAuthorities(source.getRoles().stream()
                    .map(x -> new SimpleGrantedAuthority(x.getName()))
                    .collect(Collectors.toSet()));
        }
        destination.setDeleted(source.getIs_deleted());
        destination.setAccountNonExpired(source.getIs_account_non_expired());
        destination.setAccountNonLocked(source.getIs_account_non_locked());
        destination.setCredentialsNonExpired(source.getIs_credentials_non_expired());
        destination.setEnabled(source.getIs_enabled());
    }

    @Override
    protected void postEntityCopy(JwtEntity source, User destination) {
        destination.setPassword_hash(source.getPassword());
        destination.setPhone_number(source.getPhoneNumber());
        if (source.getAuthorities() != null && !source.getAuthorities().isEmpty()) {
            destination.setRoles(source.getAuthorities().stream()
                    .map(x -> new Role(null, x.getAuthority()))
                    .toList());
        }
        destination.setIs_deleted(source.isDeleted());
        destination.setIs_credentials_non_expired(source.isAccountNonExpired());
        destination.setIs_account_non_locked(source.isAccountNonLocked());
        destination.setIs_credentials_non_expired(source.isCredentialsNonExpired());
        destination.setIs_enabled(source.isEnabled());
    }
}
