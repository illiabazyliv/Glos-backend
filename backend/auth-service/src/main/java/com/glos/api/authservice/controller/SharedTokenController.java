package com.glos.api.authservice.controller;

import com.glos.api.authservice.service.SharedService;
import com.glos.api.authservice.shared.SharedEntity;
import com.glos.api.authservice.util.security.JwtResponse;
import jakarta.ws.rs.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SharedTokenController {

    private final SharedService sharedService;

    public SharedTokenController(SharedService sharedService) {
        this.sharedService = sharedService;
    }

    @PostMapping("/sys/shared/generate")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody SharedEntity request) {
        return ResponseEntity.ok(sharedService.generateShared(request));
    }

    @GetMapping("/shared/validate")
    public ResponseEntity<?> validateSharedToken(@RequestParam("token") String token) {
        sharedService.validateShared(token);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sys/shared/destroy/{path}")
    public ResponseEntity<?> destroyShared(@PathVariable String path) {
        sharedService.destroyShared(path);
        return ResponseEntity.ok().build();
    }

}
