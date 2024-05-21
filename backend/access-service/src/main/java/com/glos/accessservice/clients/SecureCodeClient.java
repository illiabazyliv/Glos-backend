package com.glos.accessservice.clients;

import com.glos.api.entities.SecureCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@FeignClient(name = "secureCodes")
public interface SecureCodeClient
{
    @GetMapping("/{receiver}/{path}")
    ResponseEntity<SecureCode> getByReceiverAndResourcePath(@PathVariable("receiver") String receiver,
                                                                   @PathVariable("path") String path);

    @PostMapping
    ResponseEntity<?> createSecureCode(@RequestBody SecureCode secureCode);

}
