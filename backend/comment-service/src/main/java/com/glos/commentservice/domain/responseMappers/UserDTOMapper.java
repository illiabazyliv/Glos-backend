package com.glos.commentservice.domain.responseMappers;

import com.glos.api.entities.User;
import com.glos.api.entities.mappers.AbstractMapper;
import com.glos.commentservice.domain.DTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper extends AbstractMapper<User, UserDTO> {

}
