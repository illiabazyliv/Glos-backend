package com.glos.api.authservice.mapper;

import com.glos.api.authservice.dto.UserDetailsImpl;
import com.glos.api.entities.User;
import com.glos.api.entities.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper extends AbstractMapper<User, UserDetailsImpl> {

}
