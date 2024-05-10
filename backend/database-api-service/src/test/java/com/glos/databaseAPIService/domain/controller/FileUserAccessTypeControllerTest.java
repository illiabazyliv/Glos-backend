package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.AccessType;
import com.glos.api.entities.FileUserAccessType;
import com.glos.databaseAPIService.domain.filters.FileUserAccessTypeFilter;
import com.glos.databaseAPIService.domain.service.FileUserAccessTypeService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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


@WebMvcTest(FileUserAccessTypeController.class)
@ExtendWith(MockitoExtension.class)
class FileUserAccessTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FileUserAccessTypeService fileUserAccessTypeService;

    @Test
    void getAllTest() throws Exception {
        FileUserAccessTypeFilter fuatFilter = new FileUserAccessTypeFilter();
        FileUserAccessType fileUserAccessType = new FileUserAccessType();
        List<FileUserAccessType> fileUserAccessTypes = Collections.singletonList(fileUserAccessType);
        when(fileUserAccessTypeService.getAll(fuatFilter))
                .thenReturn(fileUserAccessTypes);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(fileUserAccessTypes);

        mockMvc.perform(MockMvcRequestBuilders.get("/fuat")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        Long id = 1L;
        FileUserAccessType fileUserAccessType = new FileUserAccessType();
        when(fileUserAccessTypeService.getById(id))
                .thenReturn(Optional.of(fileUserAccessType));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/fuat/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createTest() throws Exception {
        Long id = 1L;
        FileUserAccessType request = new FileUserAccessType();

        FileUserAccessType created = new FileUserAccessType();
        created.setId(id);

        when(fileUserAccessTypeService.create(ArgumentMatchers.any(FileUserAccessType.class))).thenReturn(created);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
        String expectedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/fuat")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        verify(fileUserAccessTypeService, times(1)).create(ArgumentMatchers.any(FileUserAccessType.class));
    }
    @Test
    void testCreate() throws Exception {
        Long id = 1L;
        FileUserAccessType request = new FileUserAccessType();
        FileUserAccessType updated = new FileUserAccessType();

        updated.setId(id);
        request.setId(id);

        when(fileUserAccessTypeService.update(any(Long.class) ,any(FileUserAccessType.class)))
                .thenReturn(updated);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/fuat/" , id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(fileUserAccessTypeService, times(1)).update(id, request);

    }

    @Test
    void deleteById() throws Exception{
        Long id = 1L;
        doNothing().when(fileUserAccessTypeService).deleteById(id);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/fuat/{id}",id))
                .andExpect(status().isNoContent());
    }
}