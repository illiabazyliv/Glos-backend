package com.glos.filemanagerservice.responseMappers;

import com.glos.api.entities.User;
import com.glos.filemanagerservice.DTO.UserDTO;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<UserDTO, User> {
}
