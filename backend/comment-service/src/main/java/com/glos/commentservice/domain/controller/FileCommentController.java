package com.glos.commentservice.domain.controller;

import com.glos.api.entities.Comment;
import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.facade.FileApiFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class FileCommentController
{
    private final FileApiFacade fileApiFacade;

    public FileCommentController(FileApiFacade fileApiFacade) {
        this.fileApiFacade = fileApiFacade;
    }


    @GetMapping("/{rootFullName}/comments")
    public ResponseEntity<Page<CommentDTO>> getComments(@PathVariable String rootFullName)
    {
        return ResponseEntity.ok(fileApiFacade.getFileComments(rootFullName).getBody());
    }

    @PostMapping("/{rootFullName}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment, @PathVariable String rootFullName)
    {
        return ResponseEntity.ok(fileApiFacade.createFileComment(comment ,rootFullName).getBody());
    }

    @DeleteMapping("/{rootFullName}/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("rootFullName") String rootFullName, @PathVariable("id") Long id)
    {
        fileApiFacade.deleteComment(rootFullName, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{rootFullName}/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("rootFullName") String rootFullName,
                                           @PathVariable("id") Long id,
                                           @RequestBody Comment comment)
    {
        fileApiFacade.updateComment(rootFullName, id, comment);
        return ResponseEntity.noContent().build();
    }
}
