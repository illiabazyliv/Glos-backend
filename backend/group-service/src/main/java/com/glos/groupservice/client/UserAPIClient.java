package com.glos.groupservice.client;

import com.glos.api.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user")
public interface UserAPIClient {

    @GetMapping("/username/{username}")
    ResponseEntity<User> getByUsername(@PathVariable String username);

    @GetMapping
    ResponseEntity<List<User>> getFilter(@SpringQueryMap Map<String, Object> filter);
}
