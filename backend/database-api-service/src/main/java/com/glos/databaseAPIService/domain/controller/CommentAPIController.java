package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.Comment;
import com.glos.databaseAPIService.domain.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * @author Mykola Melnyk
 */
@RestController
@RequestMapping("/comments")
public class CommentAPIController {

    private final CommentService commentService;

    public CommentAPIController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAll(@ModelAttribute Comment filter) {
        return ResponseEntity.ok(commentService.getAll(filter));
    }

    @GetMapping("/{id:\\d}")
    public ResponseEntity<Comment> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.of(commentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Comment> create(
            @RequestBody Comment request,
            UriComponentsBuilder uriBuilder
    ) {
        Comment created = commentService.create(request);
        return ResponseEntity.created(
                uriBuilder.path("/comments/{id}")
                        .build(created.getId())
        ).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(
            @PathVariable Long id,
            @RequestBody Comment request
    ) {
        commentService.update(id, request);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
