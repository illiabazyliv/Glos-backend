package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.entity.Role;
import com.glos.databaseAPIService.domain.entity.Tag;
import com.glos.databaseAPIService.domain.entityMappers.RoleMapper;
import com.glos.databaseAPIService.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService
{
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public Optional<Role> findById(Long id)
    {
        return roleRepository.findById(id);
    }

    public Role save(Role role)
    {
        return roleRepository.save(role);
    }

    public void delete(Role role)
    {
        roleRepository.delete(role);
    }

    public Role update(Role newRole, Long id)
    {
        Role role = getRoleByIdOrThrow(id);
        roleMapper.transferEntityDto(newRole, role);
        return roleRepository.save(role);
    }

    public Optional<Role> findByName(String name)
    {
        return roleRepository.findByName(name);
    }

    Role getRoleByIdOrThrow(Long id)
    {
        return findById(id).orElseThrow(() -> { return new RuntimeException("Role is not found"); });
    }
}
