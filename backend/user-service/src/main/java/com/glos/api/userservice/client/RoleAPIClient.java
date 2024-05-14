package com.glos.api.userservice.client;

import com.glos.api.entities.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "role")
public interface RoleAPIClient {

    @GetMapping("/name/{name}")
    ResponseEntity<Role> getByName(@PathVariable String name);

}
