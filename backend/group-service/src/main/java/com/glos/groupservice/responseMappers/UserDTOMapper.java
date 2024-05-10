package com.glos.groupservice.responseMappers;

import com.glos.api.entities.User;
import com.glos.groupservice.mappers.AbstractMapper;
import com.glos.groupservice.responseDTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper extends AbstractMapper<User, UserDTO> {
}
