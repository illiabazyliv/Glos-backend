package com.glos.api.userservice.client;

import com.glos.api.userservice.entities.AccessType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "accessType")
public interface AccessTypeAPIClient {

    @GetMapping("/name/{name}")
    ResponseEntity<AccessType> getByName(@PathVariable String name);

}
