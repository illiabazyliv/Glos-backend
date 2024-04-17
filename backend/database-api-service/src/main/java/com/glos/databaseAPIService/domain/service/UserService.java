package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.entity.User;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.filters.UserFilter;
import com.glos.databaseAPIService.domain.entityMappers.UserMapper;
import com.glos.databaseAPIService.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Service
public class UserService implements CrudService<User, Long>
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<User> findAllByFilter(UserFilter filter)
    {
        return userRepository.findAll();
    }


    public void delete(User user)
    {
        userRepository.delete(user);
    }


    User getUserByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> { return new RuntimeException("User is not found"); });
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


    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAll(EntityFilter filter) {
        return userRepository.findAllByFilter(filter);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(Long id, User newUser) {
        User user = getUserByIdOrThrow(id);
        userMapper.transferEntityDto(newUser, user);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(userRepository.findById(id).get());
    }
}
