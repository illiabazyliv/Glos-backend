package com.glos.filemanagerservice.clients;

import com.glos.api.entities.AccessType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "accessTypes")
public interface AccessTypeClient
{
    @GetMapping("/name/{name}")
    ResponseEntity<AccessType> getByName(@PathVariable String name);
}
