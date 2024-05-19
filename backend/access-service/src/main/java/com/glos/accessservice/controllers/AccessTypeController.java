package com.glos.accessservice.controllers;

import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.accessservice.responseDTO.AccessTypesRequestFilter;
import com.glos.accessservice.responseDTO.Page;
import com.glos.accessservice.utils.MapUtils;
import com.glos.api.entities.AccessType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/access-types")
public class AccessTypeController
{
    private final AccessTypeApiClient accessTypeApiClient;

    public AccessTypeController(AccessTypeApiClient accessTypeApiClient) {
        this.accessTypeApiClient = accessTypeApiClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessType> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(accessTypeApiClient.getById(id).getBody());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AccessType> getByName(@PathVariable String name)
    {
        return ResponseEntity.ok(accessTypeApiClient.getByName(name).getBody());
    }

    @GetMapping
    public ResponseEntity<Page<AccessType>> getByFilter(@ModelAttribute AccessTypesRequestFilter filter,
                                                        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                        @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);
        Map<String, Object> map = MapUtils.map(filter);
        Page<AccessType> accessTypes = accessTypeApiClient.getByFilter(map).getBody();
        accessTypes.setSortPattern(sort);
        return ResponseEntity.ok(accessTypes);
    }
}
