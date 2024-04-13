package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entity.Role;
import com.glos.databaseAPIService.domain.entity.Tag;
import com.glos.databaseAPIService.domain.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/roles")
public class RoleAPIController
{
    private final RoleService roleService;

    @Autowired
    public RoleAPIController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id)
    {
        return ResponseEntity.of(roleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role)
    {
        roleService.save(role);
        return ResponseEntity.created(URI.create("/v1/roles/"+role.getId())).body(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id)
    {
        roleService.delete(roleService.findById(id).get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@RequestBody Role role, @PathVariable Long id)
    {
        roleService.update(role, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Role> findRoleByName(@PathVariable String name)
    {
        return ResponseEntity.of(roleService.findByName(name));
    }
}
