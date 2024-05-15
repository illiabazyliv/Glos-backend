package com.glos.commentservice.domain.controller;

import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.DTO.UserDTO;
import com.glos.commentservice.domain.client.ExternalCommentApi;
import com.glos.commentservice.domain.client.UserApiClient;
import com.glos.commentservice.domain.entities.Comment;
import com.glos.commentservice.domain.entities.User;
import com.glos.commentservice.domain.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController
{
    private final ExternalCommentApi externalCommentApi;
    private final UserApiClient userApiClient;

    public CommentController(ExternalCommentApi externalCommentApi, UserApiClient userApiClient) {
        this.externalCommentApi = externalCommentApi;
        this.userApiClient = userApiClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getById(@PathVariable("id") Long id)
    {
        Comment comment = externalCommentApi.getById(id).getBody();
        UserDTO userDTO = new UserDTO(comment.getAuthor().getUsername());
        CommentDTO commentDTO = new CommentDTO(comment.getId(), userDTO, comment.getText(), comment.getDate());
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment)
    {
       User author = userApiClient.getUserByUsername(comment.getAuthor().getUsername()).getBody();
       comment.setAuthor(author);
       Comment created = externalCommentApi.create(comment).getBody();
       UserDTO userDTO = new UserDTO(created.getAuthor().getUsername());
       CommentDTO commentDTO = new CommentDTO(created.getId(), userDTO, created.getText(), created.getDate());
       return ResponseEntity.ok(commentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long id, @RequestBody Comment comment)
    {
        comment.setId(id);
        externalCommentApi.update(id, comment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id)
    {
        externalCommentApi.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CommentDTO>> getByFilter (@ModelAttribute Comment filter)
    {
        Map<String, Object> map = MapUtils.map(filter);
        Page<Comment> commentPage = externalCommentApi.getAllByFilter(map).getBody();
        Page<CommentDTO> commentDTOPage = commentPage.map((x) -> {return new CommentDTO(x.getId(), new UserDTO(x.getAuthor().getUsername()), x.getText(), x.getDate());});
        return ResponseEntity.ok(commentDTOPage);
    }
}
