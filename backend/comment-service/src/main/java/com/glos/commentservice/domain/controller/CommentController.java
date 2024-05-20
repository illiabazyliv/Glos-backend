package com.glos.commentservice.domain.controller;

import com.glos.api.entities.Comment;
import com.glos.api.entities.User;
import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.DTO.UserDTO;
import com.glos.commentservice.domain.facade.CommentApiFacade;
import com.glos.commentservice.domain.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController
{
    private final CommentApiFacade commentApiFacade;

    public CommentController(CommentApiFacade commentApiFacade) {
        this.commentApiFacade = commentApiFacade;
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getById(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(commentApiFacade.getById(id).getBody());
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment)
    {
       return ResponseEntity.ok(commentApiFacade.createComment(comment).getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long id, @RequestBody Comment comment)
    {
        commentApiFacade.updateComment(id, comment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id)
    {
       commentApiFacade.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CommentDTO>> getByFilter (@ModelAttribute Comment filter)
    {
       return ResponseEntity.ok(commentApiFacade.getByFilter(filter).getBody());
    }
}
