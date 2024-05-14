package com.glos.api.userservice.client;

import com.glos.api.entities.Role;
import com.glos.api.userservice.responseDTO.Page;
import com.glos.api.userservice.responseDTO.RoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "roles")
public interface RoleAPIClient
{
    @GetMapping("/name/{name}")
    ResponseEntity<Role> getRoleByName(@PathVariable String name);

    @GetMapping
    ResponseEntity<Page<Role>> getPage(@SpringQueryMap Map<String, Object> filter);
}
