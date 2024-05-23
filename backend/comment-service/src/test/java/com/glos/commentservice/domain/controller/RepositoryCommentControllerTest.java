package com.glos.commentservice.domain.controller;

import com.glos.api.entities.Comment;
import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.facade.RepositoryApiFacade;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(RepositoryCommentController.class)
@ExtendWith(MockitoExtension.class)
class RepositoryCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryApiFacade repositoryApiFacade;

    @MockBean
    private RepositoryCommentController repositoryCommentController;


//    @Test
//    void getCommentsTest() throws Exception{
//        String rootFullName = "testRootFullName";
//        CommentDTO commentDTO = new CommentDTO();
//        List<CommentDTO> commentDTOList = Collections.singletonList(commentDTO);
//        Page<CommentDTO> commentDTOPage = new PageImpl<>(commentDTOList);
//        when(repositoryApiFacade.getRepositoryComments(rootFullName)).thenReturn(ResponseEntity.ok((Page<CommentDTO>) commentDTOPage));
//
//         mockMvc.perform(MockMvcRequestBuilders.get("/repositories/{rootFullName}/comments", rootFullName)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//    }
//    @Disabled
//    @Test
//    void createCommentTest() throws Exception{
//        String rootFullName = "repo/name";
//        Comment comment = new Comment();
//        CommentDTO commentDTO = new CommentDTO();
//        when(repositoryApiFacade.createRepositoryComment(any(Comment.class), eq(rootFullName)))
//                .thenReturn(ResponseEntity.ok(commentDTO));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/repositories/{rootFullName}/comments", rootFullName)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"content\": \"Test comment\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//        verify(repositoryApiFacade, times(1)).createRepositoryComment(any(Comment.class), eq(rootFullName));
//    }
//    @Disabled
//    @Test
//    void deleteCommentTest() throws Exception{
//        String rootFullName = "repoName";
//        Long id = 1L;
//
//        when(repositoryApiFacade.deleteComment(rootFullName , id)).thenReturn(null);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/repositories/{rootFullName}/comments/{id}", rootFullName, id))
//                .andExpect(status().isNoContent());
//
//        verify(repositoryApiFacade, times(1)).deleteComment(rootFullName, id);
//    }

//    @Test
//    void updateCommentTest() throws Exception{
//        String rootFullName = "repo/name";
//        Long id = 1L;
//        Comment comment = new Comment();
//        comment.setText("Updated comment");
//
//        when(repositoryApiFacade.updateComment(eq(rootFullName), eq(id), any(Comment.class))).thenReturn(comment);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/{rootFullName}/comments/{id}", rootFullName, id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"content\": \"Updated comment\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.content").value("Updated comment"));
//
//        verify(repositoryApiFacade, times(1)).updateComment(eq(rootFullName), eq(id), any(Comment.class));
//    }
}