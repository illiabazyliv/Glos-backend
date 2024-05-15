package com.glos.api.authservice.mapper;

import com.glos.api.authservice.dto.SignUpRequest;
import com.glos.api.entities.User;
import com.glos.api.entities.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class SignUpRequestMapper extends AbstractMapper<User, SignUpRequest> {
    @Override
    protected void copyDto(User source, SignUpRequest destination, boolean hardSet) {
        destination.setUsername(source.getUsername());
        destination.setEmail(source.getEmail());
        destination.setGender(source.getGender());
        destination.setPhoneNumber(source.getPhone_number());
        destination.setPassword(source.getPassword_hash());
        destination.setBirthdate(source.getBirthdate());
        destination.setFirstName(source.getFirst_name());
        destination.setLastName(source.getLast_name());
    }

    @Override
    protected void copyEntity(SignUpRequest source, User destination, boolean hardSet) {
        destination.setUsername(source.getUsername());
        destination.setEmail(source.getEmail());
        destination.setGender(source.getGender());
        destination.setPhone_number(source.getPhoneNumber());
        destination.setPassword_hash(source.getPassword());
        destination.setBirthdate(source.getBirthdate());
        destination.setFirst_name(source.getFirstName());
        destination.setFirst_name(source.getLastName());
    }
}
