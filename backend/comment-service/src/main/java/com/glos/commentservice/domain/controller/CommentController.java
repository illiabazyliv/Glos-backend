package com.glos.commentservice.domain.controller;

import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.UserDTO;
import com.glos.commentservice.domain.client.ExternalCommentApi;
import com.glos.commentservice.domain.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/comments")
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
       UserDTO userDTO = new UserDTO(created.getAuthor().getId());
       CommentDTO commentDTO = new CommentDTO(created.getId(), userDTO, created.getText(), created.getDate());
       return ResponseEntity.ok(commentDTO);
    }
}
