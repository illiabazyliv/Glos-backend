package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.RepositoryUserAccessType;
import com.glos.databaseAPIService.domain.filters.RepositoryUserAccessTypeFilter;
import com.glos.databaseAPIService.domain.service.RepositoryUserAccessTypeService;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RepositoryUserAccessTypeAPIController.class)
class RepositoryUserAccessTypeAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RepositoryUserAccessTypeService service;
    @Test
    void getRUATByIdTest() throws Exception{
        Long id = 1L;
        RepositoryUserAccessType ruat = new RepositoryUserAccessType();

        when(service.getById(id)).thenReturn(Optional.of(ruat));

        mockMvc.perform(MockMvcRequestBuilders.get("/ruat/{id}" , id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createRUATTest() throws Exception{
        RepositoryUserAccessType request = new RepositoryUserAccessType();
        RepositoryUserAccessType response = new RepositoryUserAccessType();

        when(service.create(any(RepositoryUserAccessType.class))).thenReturn(response);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ruat")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).create(any(RepositoryUserAccessType.class));
    }

    @Test
    void deleteRUATTest() throws Exception{
        Long id = 1L;

        doNothing().when(service).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ruat/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateRUATTest() throws Exception{
        Long id = 1L;
        RepositoryUserAccessType request = new RepositoryUserAccessType();
        RepositoryUserAccessType updated = new RepositoryUserAccessType();
        when(service.update(id, request)).thenReturn(updated);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/ruat/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllRUATTest() throws Exception{
        RepositoryUserAccessType ruat = new RepositoryUserAccessType();

        List<RepositoryUserAccessType> ruats = List.of(ruat);

        when(service.getAll()).thenReturn(ruats);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(ruats);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruat")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(service, times(1)).getAll();

    }

    @Test
    void getRUATByFilterTest() throws Exception{
        RepositoryUserAccessTypeFilter filter = new RepositoryUserAccessTypeFilter();

        RepositoryUserAccessType ruat = new RepositoryUserAccessType();
        ruat.setId(1L);
        List<RepositoryUserAccessType> ruats =List.of(ruat);

        when(service.getAll(any(RepositoryUserAccessTypeFilter.class))).thenReturn(ruats);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(ruats);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruat/filter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(service, times(1)).getAll(any(RepositoryUserAccessTypeFilter.class));

    }
}