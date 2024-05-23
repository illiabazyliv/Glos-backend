package com.glos.commentservice.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.api.entities.Comment;
import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.facade.FileApiFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(FileCommentController.class)
@ExtendWith(MockitoExtension.class)
class FileCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileApiFacade fileApiFacade;

    @MockBean
    private FileCommentController fileCommentController;

//    @Test
//    void getCommentsTest() throws Exception {
//        CommentDTO commentDTO = new CommentDTO();
//
//        List<CommentDTO> commentDTOList = Collections.singletonList(commentDTO);
//
//        PageRequest pageRequest = PageRequest.of(0, commentDTOList.size());
//        Page<CommentDTO> mockPage = new PageImpl<>(commentDTOList, pageRequest, commentDTOList.size());
//
//        ResponseEntity<Page<CommentDTO>> responseEntity = ResponseEntity.ok(mockPage);
//
//        when(fileApiFacade.getFileComments(anyString())).thenReturn(responseEntity);
//
//        mockMvc.perform(get("/files/rootFullName/comments"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray())
//                .andExpect(jsonPath("$.content[0]").exists());
//
//        verify(fileApiFacade, times(1)).getFileComments(anyString());
//    }
    @Disabled
    @Test
    void createCommentTest() throws Exception {
        Comment comment = new Comment();
        CommentDTO commentDTO = new CommentDTO();
        when(fileApiFacade.createFileComment(any(Comment.class), anyString())).thenReturn(ResponseEntity.ok(commentDTO));

        mockMvc.perform(MockMvcRequestBuilders.post("/files/rootFullName/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Comment.\"}"))
                .andExpect(status().isOk());

        verify(fileApiFacade, times(1)).createFileComment(any(Comment.class), anyString());
    }
    @Disabled
    @Test
    void deleteCommentTest() throws Exception {
        String rootFullName = "testRootFullName";
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/files/{rootFullName}/comments/{id}", rootFullName, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Disabled
    @Test
    void updateCommentTest() throws Exception {
        String rootFullName = "testRootFullName";
        Long id = 1L;
        Comment comment = new Comment();
        comment.setId(id);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/files/{rootFullName}/comments/{id}", rootFullName, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isNoContent());
    }
}