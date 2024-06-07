package com.glos.filemanagerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.DTO.FileRequest;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.clients.FileStorageClient;
import com.glos.filemanagerservice.clients.RepositoryStorageClient;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.facade.FileApiFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileUploadController.class)
@ExtendWith(MockitoExtension.class)

class FileUploadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileApiFacade fileApiFacade;

    @MockBean
    private RepositoryStorageClient repositoryStorageClient;

    @MockBean
    private FileClient fileClient;

    @MockBean
    private FileStorageClient fileStorageClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void uploadFileTest()throws Exception {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(1L);
        List<FileDTO> fileDTOList = Collections.singletonList(fileDTO);
        when(fileApiFacade.uploadFiles(any())).thenReturn(ResponseEntity.ok(fileDTOList));

        FileRequest fileRequest = new FileRequest();

        mockMvc.perform(MockMvcRequestBuilders.put("/files/upload")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("fileRequests", fileRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
    @Test
    void downloadFileTest()throws Exception {
        ByteArrayResource resource = new ByteArrayResource("test content".getBytes());
        when(fileApiFacade.downloadFiles(any())).thenReturn(ResponseEntity.ok(resource));

        List<String> rootFullNames = Collections.singletonList("someFileName");

        mockMvc.perform(MockMvcRequestBuilders.get("/files/download")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rootFullNames)))
                .andExpect(status().isOk())
                .andExpect(content().string("test content"));
    }
    @Test
    void downloadRepository() throws Exception {
        ByteArrayResource resource = new ByteArrayResource("repository content".getBytes());
        when(repositoryStorageClient.getRepository(any())).thenReturn(ResponseEntity.ok(resource));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/someRootFullName/download"))
                .andExpect(status().isOk())
                .andExpect(content().string("repository content"));
    }
}