package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.Comment;
import com.glos.databaseAPIService.domain.service.CommentService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentAPIController.class)
@ExtendWith(MockitoExtension.class)
class CommentAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    public void getAllTest() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Test comment");
        List<Comment> comments = List.of(comment);
        when(commentService.getAll(any(Comment.class))).thenReturn(comments);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(comments);
        mockMvc.perform(MockMvcRequestBuilders.get("/comments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 1L;
        Comment comment = new Comment();
        comment.setId(id);
        comment.setText("Test comment");
        when(commentService.getById(id)).thenReturn(Optional.of(comment));
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(comment);
        mockMvc.perform(MockMvcRequestBuilders.get("/comments/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void createTest() throws Exception {
        Comment request = new Comment();
        request.setText("Test comment");
        Comment created = new Comment();
        created.setId(1L);
        created.setText("Comment test num two");
        when(commentService.create(any(Comment.class))).thenReturn(created);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/comments/" + created.getId()));
    }

    @Test
    public void updateTest() throws Exception {
        Long id = 1L;
        Comment request = new Comment();
        request.setText("Updated comment");
        Comment updated = new Comment();
        updated.setId(id);
        updated.setText("Updated comment");
        when(commentService.update(id, request)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/comments/" + id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdTest() throws Exception {
        Long id = 1L;
        doNothing().when(commentService).deleteById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/" + id))
                .andExpect(status().isNoContent());

    }
}