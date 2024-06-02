package com.glos.accessservice.controllers;

import com.glos.accessservice.facade.SharedFacade;
import com.glos.accessservice.responseDTO.SharedTokenResponse;
import com.pathtools.Path;
import com.pathtools.PathParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SharedController
{
    private final SharedFacade sharedFacade;

    public SharedController(SharedFacade sharedFacade) {
        this.sharedFacade = sharedFacade;
    }


    @PostMapping("/{rootFullName}")
    public ResponseEntity<SharedTokenResponse> generateSharedToken(@PathVariable String rootFullName)
    {
        Path path = new PathParser().parse(rootFullName);
        return ResponseEntity.ok(sharedFacade.generate(path.getPath()).getBody());
    }
}
