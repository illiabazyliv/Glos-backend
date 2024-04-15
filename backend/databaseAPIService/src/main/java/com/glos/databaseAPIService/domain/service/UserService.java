package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.entity.User;
import com.glos.databaseAPIService.domain.filters.UserFilter;
import com.glos.databaseAPIService.domain.entityMappers.UserMapper;
import com.glos.databaseAPIService.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }

    public List<User> findAllByFilter(UserFilter filter)
    {
        return userRepository.findAll();
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public void delete(User user)
    {
        userRepository.delete(user);
    }

    public User update(User newUser, Long id)
    {
        User user = getUserByIdOrThrow(id);
        userMapper.transferEntityDto(newUser, user);
        return userRepository.save(user);
    }

    User getUserByIdOrThrow(Long id)
    {
        return findById(id).orElseThrow(() -> { return new RuntimeException("User is not found"); });
    }

    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber)
    {
        return userRepository.findByPhoneNumber(phoneNumber);
    }


}
