package com.glos.databaseAPIService.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.api.entities.AccessType;
import com.glos.api.entities.File;
import com.glos.api.entities.Repository;
import com.glos.databaseAPIService.domain.filters.FileFilter;
import com.glos.databaseAPIService.domain.service.AccessTypeService;
import com.glos.databaseAPIService.domain.service.FileService;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileAPIController.class)
class FileAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FileService fileService;

    @Test
    void getFileByIDTest() throws Exception {
        Long id = 1L;
        File file = new File();
        file.setId(id);
        when(fileService.findById(id))
                .thenReturn(Optional.of(file));
        mockMvc.perform(MockMvcRequestBuilders.
                get("/files/{id}" , id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createFileTest() throws Exception{
        Long id = 1L;
        File file = new File();
        file.setId(id);
        when(fileService.save(file)).thenReturn(file);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(file);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/files")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isCreated())
                .andExpect(header()
                        .string("Location", "/v1/files/" + file
                                .getId()));
    }

    @Test
    void updateFileTest() throws Exception {
        Long id = 1L;
        File file = new File();
        File newFile = new File();
        newFile.setId(id);

        when(fileService.update(file,id)).thenReturn(newFile);
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(file);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/files/{id}", id)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFileTest() throws Exception {
        Long id = 1L;

        doNothing().when(fileService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/files/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void getFilesByRepositoryTest() throws Exception {
        Long id = 1L;
        File file = new File();
        List<File> files = Collections.singletonList(file);
        when(fileService.findAllByRepositoryId(id)).thenReturn(files);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(files);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/files/repository/{id}" , id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getFileByRootFullNameTest() throws Exception {
        String rootFullName = "root+to+file.txt";
        File file = new File();
        when(fileService.findByRootFullName(rootFullName)).thenReturn(Optional.of(file));
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(file);
        mockMvc.perform(MockMvcRequestBuilders.get("/files/root-fullname/{rootFullName}",rootFullName)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getFilesByFilterTest() throws Exception {
        File filter = new File();
        File file = new File();
        List<File> files =List.of();
        when(fileService.findAllByFilter(filter)).thenReturn(files);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(files);

        mockMvc.perform(MockMvcRequestBuilders.get("/files")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(content().json(expectedJson));
    }


}