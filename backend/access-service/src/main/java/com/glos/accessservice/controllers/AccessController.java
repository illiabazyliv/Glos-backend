package com.glos.accessservice.controllers;

import com.glos.accessservice.facade.AccessFacade;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessController {

    private final AccessFacade accessFacade;

    public AccessController(AccessFacade accessFacade) {
        this.accessFacade = accessFacade;
    }

    @GetMapping("/access/{readWrite}/{rootFullName}/available/{username}")
    public ResponseEntity<?> isAvailable(
            @PathVariable("readWrite") String readWrite,
            @PathVariable("rootFullName") String rootFullName,
            @PathVariable("username") String username
    ) {
        AccessRequest request = new AccessRequest();
        if ("read".equals(readWrite)) {
            request.setReadOnly(true);
        } else if ("write".equals(readWrite) || "readwrite".equals(readWrite)) {
            request.setReadOnly(false);
        } else {
            return ResponseEntity.badRequest().build();
        }
        request.setPath(rootFullName);
        request.setUsername(username);
        return accessFacade.available(request);
    }

}
