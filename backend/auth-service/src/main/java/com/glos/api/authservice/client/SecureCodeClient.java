package com.glos.api.authservice.client;

import com.glos.api.entities.SecureCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "secureCodes")
public interface SecureCodeClient
{

    @GetMapping("/{rootFullName}")
    ResponseEntity<SecureCode> getByResourcePath(@PathVariable("rootFullName") String rootFullName);

}