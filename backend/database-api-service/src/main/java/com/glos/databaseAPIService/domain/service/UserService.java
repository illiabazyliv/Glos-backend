package com.glos.databaseAPIService.domain.service;


import com.glos.databaseAPIService.domain.entities.Group;
import com.glos.databaseAPIService.domain.entities.Role;
import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.entityMappers.UserMapper;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.GroupRepository;
import com.glos.databaseAPIService.domain.repository.RoleRepository;
import com.glos.databaseAPIService.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    User getUserByIdOrThrow(Long id)
    {
        collect();
        return getById(id).orElseThrow(() -> { return new ResourceNotFoundException("User is not found"); });
    }

    public Optional<User> findByUsername(String username)
    {
        collect();
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email)
    {
        collect();
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber)
    {
        collect();
        return userRepository.findByPhoneNumber(phoneNumber);
    }


    @Transactional
    @Override
    public User create(User user) {
        collect();
        assignRoles(user);
        assignGroups(user);
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedDateTime(now);
        user.setUpdatedDataTime(now);
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

    public Page<User> getPage(Pageable pageable) {
        collect();
        return userRepository.findAll(pageable);
    }

    public Page<User> getPageByFilter(User group, Pageable pageable) {
        collect();
        return userRepository.findAll(Example.of(group), pageable);
    }

    @Override
    public List<User> getAll() {
        collect();
        return userRepository.findAll();
    }

    public List<User> getAll(User filter) {
        collect();
        List<User> list = userRepository.findAll(Example.of(filter));
        return list.stream()
                .filter(x -> filter.getRoles() == null || x.getRoles().containsAll(filter.getRoles()))
                .filter(x -> filter.getGroups() == null || x.getGroups().containsAll(filter.getGroups()))
                .toList();
    }

    @Override
    public List<User> getAll(EntityFilter filter) {
        collect();
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> getById(Long id) {
        collect();
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public User update(Long id, User newUser) {
        collect();
        User user = getUserByIdOrThrow(id);
        newUser.setId(null);
        boolean deleted = user.getIs_deleted();
        boolean isAccountNonLocked = user.getIs_account_non_locked();
        boolean isEnabled = user.getIs_enabled();
        userMapper.transferEntityDto(newUser, user);
        LocalDateTime now = LocalDateTime.now();
        user.setUpdatedDataTime(now);
        if (user.getIs_enabled() != isEnabled) {
            user.setDisabledDateTime((user.getIs_enabled()) ? null : now);
        }
        if (user.getIs_account_non_locked() != isAccountNonLocked) {
            user.setBlockedDateTime((user.getIs_account_non_locked()) ? null : now);
        }
        if (user.getIs_deleted() != deleted) {
            user.setDeletedDateTime((user.getIs_deleted()) ? user.getDeletedDateTime() : null);
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id) {
        User user = getUserByIdOrThrow(id);
        delete(user);
    }

    @Transactional
    public void delete(User user)
    {
        userRepository.delete(user);
    }


    private void collect() {
        List<User> users = userRepository.findAll();
        List<User> users2 = users.stream()
                .filter(x -> x.getIs_deleted() != null && x.getIs_deleted())
                .filter(x -> x.getDeletedDateTime() != null)
                .filter(x -> {
                    LocalDateTime now = LocalDateTime.now();
                    return x.getDeletedDateTime().isBefore(now);
                }).toList();
        userRepository.deleteAll(users2);
    }
}
