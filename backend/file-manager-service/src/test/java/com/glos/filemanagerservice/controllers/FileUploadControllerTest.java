package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.entities.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileUploadController.class)
@ExtendWith(MockitoExtension.class)

class FileUploadControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FileClient fileClient;
//
//    @InjectMocks
//    private FileUploadController fileUploadController;
//
    @Test
    void uploadFileTest()throws Exception {
//        File file = new File();
//        file.setRootFilename("testFile");
//        FileDTO fileDTO = new FileDTO();
//        fileDTO.setId(1L);
//        fileDTO.setRootFilename("testFile");
//
//        when(fileClient.createFile(any(File.class))).thenReturn(ResponseEntity.ok(fileDTO));
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/files/{filename}/upload", "testFile")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"testFile\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "/files/1"));
    }
//
    @Test
    void downloadFileTest()throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/files/{filename}/download", "testFile")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
    }
}