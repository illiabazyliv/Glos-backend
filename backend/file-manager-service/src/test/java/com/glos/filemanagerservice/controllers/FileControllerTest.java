package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.facade.FileApiFacade;
import com.glos.filemanagerservice.responseMappers.FileDTOMapper;
import com.glos.filemanagerservice.responseMappers.FileRequestMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(FileController.class)
@ExtendWith(MockitoExtension.class)
class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileClient fileClient;

    @MockBean
    private RepositoryClient repositoryClient;

    @MockBean
    private FileDTOMapper fileDTOMapper;

    @MockBean
    private FileRequestMapper fileRequestMapper;

    @MockBean
    private FileApiFacade fileApiFacade;

    @Test
    void getFileByIdTest() throws Exception {
        FileDTO fileDTO = new FileDTO();
        Long id = 1L;
        fileDTO.setId(id);
        when(fileClient.getFileByID(anyLong())).thenReturn(ResponseEntity.ok(fileDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/files/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));
    }

    @Test
    void updateTest() throws Exception {
        File file = new File();
        Long id = 1L;
        file.setId(id);
        file.setUpdateDate(LocalDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders.put("/files/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"test\"}"))
                .andExpect(status().isNoContent());


    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/files/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByRootFullNameTest() throws Exception {
        FileDTO mockFileDTO = new FileDTO();
        when(fileClient.getFileByRootFullName(anyString())).thenReturn(ResponseEntity.ok(mockFileDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/files/root-fullname/{rootFullName}", "testRootFullName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void getByRepositoryTest() throws Exception {
        Page<FileDTO> mockPage = new Page<>();
        mockPage.setContent(Collections.emptyList());
        mockPage.setNumber(0);
        mockPage.setSize(10);
        mockPage.setTotalElements(0);

        when(fileApiFacade.getFileByRepository(anyLong(), anyInt(), anyInt(), anyString()))
                .thenReturn(ResponseEntity.ok(mockPage));

        mockMvc.perform(MockMvcRequestBuilders.get("/files/repository/{repositoryId}", 1L)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[],\"number\":0,\"size\":10,\"totalElements\":0}"));
    }

    @Test
    void getByFilterTest() throws Exception {
        Page<FileDTO> mockPage = new Page<>();
        mockPage.setContent(Collections.emptyList());
        mockPage.setNumber(0);
        mockPage.setSize(10);
        mockPage.setTotalElements(0);

        when(fileApiFacade.getFilesByFilter(any(File.class), anyInt(), anyInt(), anyString()))
                .thenReturn(ResponseEntity.ok(mockPage));

        mockMvc.perform(MockMvcRequestBuilders.get("/files")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[],\"number\":0,\"size\":10,\"totalElements\":0}"));
    }
}