package com.glos.databaseAPIService.domain.service;


import com.glos.api.entities.File;
import com.glos.api.entities.Group;
import com.glos.api.entities.Role;
import com.glos.api.entities.User;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.entityMappers.UserMapper;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.GroupRepository;
import com.glos.databaseAPIService.domain.repository.RoleRepository;
import com.glos.databaseAPIService.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Service
public class UserService implements CrudService<User, Long>
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            RoleRepository roleRepository,
            GroupRepository groupRepository
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
    }

    @Transactional
    public void delete(User user)
    {
        userRepository.delete(user);
    }


    User getUserByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> { return new ResourceNotFoundException("User is not found"); });
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


    @Transactional
    @Override
    public User create(User user) {
        assignRoles(user);
        assignGroups(user);
        return userRepository.save(user);
    }

    private User assignRoles(User user) {
        final List<Role> roles = user.getRoles();
        if (roles != null) {
            final List<Role> found = roles.stream().map(x -> {
                if (x.getId() != null) {
                    return roleRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Id of Role is not found")
                    );
                }
                return x;
            }).toList();
            user.setRoles(found);
        }
        return user;
    }
    private User assignGroups(User user) {
        final List<Group> groups = user.getGroups();
        if (groups != null) {
            final List<Group> found = groups.stream().map(x -> {
                if (x.getId() != null) {
                    return groupRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Id of Group is not found")
                    );
                }
                return x;
            }).toList();
            user.setGroups(found);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> getAll(User filter) {
        return userRepository.findAll(Example.of(filter));
    }

    @Override
    public List<User> getAll(EntityFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public User update(Long id, User newUser) {
        User user = getUserByIdOrThrow(id);
        newUser.setId(null);
        userMapper.transferEntityDto(newUser, user);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        getUserByIdOrThrow(id);
        userRepository.deleteById(id);
    }
}
