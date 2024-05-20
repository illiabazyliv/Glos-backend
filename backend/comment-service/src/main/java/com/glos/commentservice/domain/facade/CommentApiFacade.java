package com.glos.commentservice.domain.facade;

import com.glos.api.entities.Comment;
import com.glos.api.entities.User;
import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.DTO.UserDTO;
import com.glos.commentservice.domain.client.ExternalCommentApi;
import com.glos.commentservice.domain.client.UserApiClient;
import com.glos.commentservice.domain.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public class CommentApiFacade
{
    private final ExternalCommentApi externalCommentApi;
    private final UserApiClient userApiClient;

    public CommentApiFacade(ExternalCommentApi externalCommentApi, UserApiClient userApiClient) {
        this.externalCommentApi = externalCommentApi;
        this.userApiClient = userApiClient;
    }


    public ResponseEntity<CommentDTO> getById(Long id)
    {
        Comment comment = externalCommentApi.getById(id).getBody();
        UserDTO userDTO = new UserDTO(comment.getAuthor().getId(), comment.getAuthor().getUsername());
        CommentDTO commentDTO = new CommentDTO(comment.getId(), userDTO, comment.getText(), comment.getDate());
        return ResponseEntity.ok(commentDTO);
    }

    public ResponseEntity<CommentDTO> createComment(Comment comment)
    {
        User author = userApiClient.getUserByUsername(comment.getAuthor().getUsername()).getBody();
        comment.setAuthor(author);
        Comment created = externalCommentApi.create(comment).getBody();
        UserDTO userDTO = new UserDTO(created.getAuthor().getId(), created.getAuthor().getUsername());
        CommentDTO commentDTO = new CommentDTO(created.getId(), userDTO, created.getText(), created.getDate());
        return ResponseEntity.ok(commentDTO);
    }


    public ResponseEntity<?> updateComment(Long id, Comment comment)
    {
        comment.setId(id);
        externalCommentApi.update(id, comment);
        return ResponseEntity.noContent().build();
    }


    public ResponseEntity<?> deleteComment(Long id)
    {
        externalCommentApi.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<CommentDTO>> getByFilter (Comment filter)
    {
        Map<String, Object> map = MapUtils.map(filter);
        Page<Comment> commentPage = externalCommentApi.getAllByFilter(map).getBody();
        Page<CommentDTO> commentDTOPage = commentPage.map((x) -> {return new CommentDTO(x.getId(), new UserDTO(x.getAuthor().getId(), x.getAuthor().getUsername()), x.getText(), x.getDate());});
        return ResponseEntity.ok(commentDTOPage);
    }
}
