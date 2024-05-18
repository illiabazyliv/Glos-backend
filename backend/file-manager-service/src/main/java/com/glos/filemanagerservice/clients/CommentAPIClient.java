package com.glos.filemanagerservice.clients;

import com.glos.api.entities.Comment;
import com.glos.filemanagerservice.DTO.CommentDTO;
import com.glos.filemanagerservice.DTO.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "comments")
public interface CommentAPIClient
{

    @GetMapping("/{id}")
    ResponseEntity<CommentDTO> getById(@PathVariable("id") Long id);
    @PostMapping
    ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment);
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteComment(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    ResponseEntity<?> updateComment(@PathVariable("id") Long id, @RequestBody Comment comment);
    @GetMapping
    ResponseEntity<Page<CommentDTO>> getByFilter (@SpringQueryMap Map<String, Object> filter);
}
