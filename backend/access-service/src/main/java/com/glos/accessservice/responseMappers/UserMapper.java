package com.glos.accessservice.responseMappers;

import com.glos.accessservice.responseDTO.UserDTO;
import com.glos.api.entities.User;
import com.glos.api.entities.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<UserDTO, User> {
}
