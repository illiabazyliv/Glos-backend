package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.Repository;
import com.glos.api.entities.User;
import com.glos.databaseAPIService.domain.service.RepositoryService;
import com.glos.databaseAPIService.domain.util.PathUtils;
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

@WebMvcTest(RepositoryAPIController.class)
@ExtendWith(MockitoExtension.class)
class RepositoryAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryService repositoryService;

    @Test
    public void getRepositoryTest() throws Exception {
        Long id = 1L;
        Repository repository = new Repository();
        repository.setId(id);

        when(repositoryService.findById(id)).thenReturn(Optional.of(repository));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/repositories/{id}" , id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createRepositoryTest() throws Exception {
        Repository repository = new Repository();
        Repository response = new Repository();
        User owner = new User();
        owner.setId(1L);
        response.setId(1L);
        response.setOwner(owner);
        repository.setOwner(owner);

        when(repositoryService.save(any(Repository.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(repository);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/repositories")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(repositoryService, times(1)).save(any(Repository.class));
    }

    @Test
    void deleteRepositoryTest() throws Exception {
        Long id = 1L;

        doNothing().when(repositoryService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/repositories/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateRepositoryTest() throws Exception {
        Long id = 1L;
        Repository request = new Repository();
        Repository updated = new Repository();

        when(repositoryService.update(request, id)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/repositories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void getRepositoriesByOwnerIdTest() throws Exception {
        Long id = 1L;
        Repository repository = new Repository();
        repository.setId(1L);

        List<Repository> repositories =List.of(repository);

        when(repositoryService.findAllByOwnerId(id)).thenReturn(repositories);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(repositories);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/repositories/owner-id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(repositoryService, times(1)).findAllByOwnerId(id);
    }

    @Test
    void getRepositoryByRootFullNameTest() throws Exception {
        String rootFullName = "rootFullName";
        String ordinalRootFullName = PathUtils.originalPath(rootFullName);

        Repository repository = new Repository();
        repository.setId(1L);

        when(repositoryService.findByRootFullName(ordinalRootFullName)).thenReturn(Optional.of(repository));

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(repository);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/repositories/root-full-name/" + rootFullName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(repositoryService, times(1)).findByRootFullName(ordinalRootFullName);
    }

    @Test
    void getRepositoriesByFilterTest() throws Exception {
        Repository filter = new Repository();
        Repository repository = new Repository();
        repository.setId(1L);

        List<Repository> repositories = List.of(repository);

        when(repositoryService.findAllByFilter(filter)).thenReturn(repositories);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(repositories);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/repositories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(repositoryService, times(1)).findAllByFilter(any(Repository.class));
    }
}