package com.glos.commentservice.domain.controller;

import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.UserDTO;
import com.glos.commentservice.domain.client.ExternalCommentApi;
import com.glos.commentservice.domain.entities.Comment;
import com.glos.commentservice.domain.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController
{
    private final ExternalCommentApi externalCommentApi;
    @Autowired
    public CommentController(ExternalCommentApi externalCommentApi) {
        this.externalCommentApi = externalCommentApi;
    }


    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment)
    {
       Comment created = externalCommentApi.create(comment).getBody();
        UserDTO userDTO = new UserDTO(created.getAuthor().getId(), created.getAuthor().getUsername());
       CommentDTO commentDTO = new CommentDTO(comment.getId(), userDTO, created.getText(), created.getDate());
        return ResponseEntity.ok(commentDTO);
    }
}
