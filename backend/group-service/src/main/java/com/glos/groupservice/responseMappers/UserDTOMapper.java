package com.glos.groupservice.responseMappers;

import com.glos.api.entities.User;
import com.glos.groupservice.mappers.AbstractMapper;
import com.glos.groupservice.dto.UserDTO;
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
