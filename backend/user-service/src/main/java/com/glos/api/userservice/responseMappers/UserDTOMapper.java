package com.glos.api.userservice.responseMappers;

import com.glos.api.entities.User;
import com.glos.api.userservice.mappers.AbstractMapper;
import com.glos.api.userservice.responseDTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper extends AbstractMapper<User, UserDTO> {
    @Override
    protected void copyDto(User source, UserDTO destination, boolean hardSet) {
        destination.setId(source.getId());
        destination.setUsername(source.getUsername());
    }

    @Override
    protected void copyEntity(UserDTO source, User destination, boolean hardSet) {
        destination.setUsername(source.getUsername());
        destination.setId(source.getId());
    }
}
