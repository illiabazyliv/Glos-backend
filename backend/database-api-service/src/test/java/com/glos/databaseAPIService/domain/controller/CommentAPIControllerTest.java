package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.Comment;
import com.glos.databaseAPIService.domain.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentAPIControllerTest {

    @InjectMocks
    CommentAPIController commentAPIController;

    @Mock
    CommentService commentService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllTest() {
        when(commentService.getAll(any(Comment.class))).thenReturn(Arrays.asList(new Comment(), new Comment()));
        ResponseEntity<List<Comment>> responseEntity = commentAPIController.getAll(new Comment());
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void getByIdTest() {
        Comment comment = new Comment();
        comment.setId(1L);
        when(commentService.getById(1L)).thenReturn(Optional.of(comment));
        ResponseEntity<Comment> responseEntity = commentAPIController.getById(1L);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1L, responseEntity.getBody().getId());
    }

    @Test
    void createTest(UriComponentsBuilder uriBuilder) {
        Comment request = new Comment();
        request.setText("Test comment");
        Comment createdComment = new Comment();
        createdComment.setId(1L);
        createdComment.setText(request.getText());
        when(commentService.create(any(Comment.class))).thenReturn(createdComment);
        ResponseEntity<Comment> responseEntity = commentAPIController.create(request, uriBuilder);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(createdComment, responseEntity.getBody());
    }


    @Test
    void updateTest() {
        Long id = 1L;
        Comment request = new Comment();
        request.setText("Updated comment");
        when(commentService.update(same(id), any(Comment.class))).thenReturn(request);
        ResponseEntity<Comment> responseEntity = commentAPIController.update(id , request);
        assertEquals(204, responseEntity.getStatusCodeValue());
        verify(commentService, times(1)).update(id, request);
    }

    @Test
    void deleteByIdTest() {
        Long id = 1L;
        doNothing().when(commentService).deleteById(id);

        ResponseEntity<?> responseEntity = commentAPIController.deleteById(id);

        assertEquals(204, responseEntity.getStatusCodeValue());
        verify(commentService, times(1)).deleteById(id);

    }
}