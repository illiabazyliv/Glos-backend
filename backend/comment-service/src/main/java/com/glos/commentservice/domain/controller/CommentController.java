package com.glos.commentservice.domain.controller;

import com.glos.commentservice.domain.DTO.CommentDTO;
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
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO)
    {
        commentRepository.createComment(commentDTO);
        return ResponseEntity.of(Optional.of(commentDTO));
    }
}
