package com.glos.commentservice.domain.controller;

import com.glos.api.entities.Comment;
import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.facade.RepositoryApiFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class RepositoryCommentController
{
    private final RepositoryApiFacade repositoryApiFacade;

    public RepositoryCommentController(RepositoryApiFacade repositoryApiFacade) {
        this.repositoryApiFacade = repositoryApiFacade;
    }

    @GetMapping("/{rootFullName}/comments")
    public ResponseEntity<Page<CommentDTO>> getComments(@PathVariable String rootFullName)
    {
        return ResponseEntity.ok(repositoryApiFacade.getRepositoryComments(rootFullName).getBody());
    }

    @PostMapping("/{rootFullName}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment , @PathVariable String rootFullName)
    {
        return ResponseEntity.ok(repositoryApiFacade.createRepositoryComment(comment ,rootFullName).getBody());
    }

    @DeleteMapping("/{rootFullName}/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("rootFullName") String rootFullName, @PathVariable("id") Long id)
    {
        repositoryApiFacade.deleteComment(rootFullName, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{rootFullName}/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("rootFullName") String rootFullName,
                                           @PathVariable("id") Long id,
                                           @RequestBody Comment comment)
    {
        repositoryApiFacade.updateComment(rootFullName, id, comment);
        return ResponseEntity.noContent().build();
    }
}
