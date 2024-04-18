package com.glos.databaseAPIService.domain.service;


import com.glos.api.entities.Role;
import com.glos.databaseAPIService.domain.entityMappers.RoleMapper;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Service
public class RoleService implements CrudService<Role, Long>
{
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    Role getRoleByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> { return new RuntimeException("Role is not found"); });
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAll() {
        return null;
    }

    @Override
    public List<Role> getAll(EntityFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Role> getById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role update(Long id, Role newRole) {

        Role role = getRoleByIdOrThrow(id);
        roleMapper.transferEntityDto(newRole, role);
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.delete(roleRepository.findById(id).get());
    }

    public Optional<Role> findByName(String name)
    {
        return roleRepository.findByName(name);
    }
}
