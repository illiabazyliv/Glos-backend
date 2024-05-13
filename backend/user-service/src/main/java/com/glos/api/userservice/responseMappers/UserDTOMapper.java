package com.glos.api.userservice.responseMappers;

import com.glos.api.entities.Role;
import com.glos.api.entities.User;
import com.glos.api.userservice.mappers.AbstractMapper;
import com.glos.api.userservice.responseDTO.RoleDTO;
import com.glos.api.userservice.responseDTO.UserDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDTOMapper extends AbstractMapper<User, UserDTO> {
    @Override
    protected void copyDto(User source, UserDTO destination, boolean hardSet) {
        destination.setId(source.getId());
        destination.setUsername(source.getUsername());
        destination.setPassword_hash(source.getPassword_hash());
        destination.setEmail(source.getEmail());
        destination.setPhone_number(source.getPhone_number());
        destination.setGender(source.getGender());
        destination.setFirst_name(source.getFirst_name());
        destination.setLast_name(source.getLast_name());
        destination.setBirthdate(source.getBirthdate());
        destination.setRoles(source.getRoles().stream().map((x) -> {return new RoleDTO(x.getName());
        }).toList());
        destination.setEnabled(source.getIs_enabled());
        destination.setDeleted(source.getIs_deleted());
    }

    @Override
    protected void copyEntity(UserDTO source, User destination, boolean hardSet) {
        destination.setId(source.getId());
        destination.setUsername(source.getUsername());
        destination.setPassword_hash(source.getPassword_hash());
        destination.setEmail(source.getEmail());
        destination.setPhone_number(source.getPhone_number());
        destination.setGender(source.getGender());
        destination.setFirst_name(source.getFirst_name());
        destination.setLast_name(source.getLast_name());
        destination.setBirthdate(source.getBirthdate());
        destination.setRoles(source.getRoles().stream().map((x) -> {
            Role r = new Role();
            r.setName(x.name());
            return  r;
        }).toList());
        destination.setIs_enabled(source.getEnabled());
        destination.setIs_deleted(source.getDeleted());
    }

}
