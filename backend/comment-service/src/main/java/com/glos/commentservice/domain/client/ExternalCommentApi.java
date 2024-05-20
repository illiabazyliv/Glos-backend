package com.glos.commentservice.domain.client;

import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.entities.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "comment")
public interface ExternalCommentApi {

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<Comment> create (@RequestBody Comment comment);

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Comment request);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id);

    @GetMapping
    public ResponseEntity<Page<Comment>> getAllByFilter(@SpringQueryMap Map<String, Object> filter);
}
