package com.glos.accessservice.clients;

import com.glos.accessservice.responseDTO.JwtResponse;
import com.glos.accessservice.responseDTO.SharedEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth")
public interface AuthClient
{
    @PostMapping("/sys/shared/generate")
    ResponseEntity<JwtResponse> generateToken(@RequestBody SharedEntity sharedEntity);
}
