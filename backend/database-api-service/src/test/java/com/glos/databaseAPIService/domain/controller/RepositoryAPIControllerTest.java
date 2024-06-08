package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.RepositoryService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import java.lang.Long;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @MockBean
    private RepositoryDTOMapper mapper;
    @MockBean
    private UserDTOMapper userDTOMapper;

    @Test
    public void getRepositoryTest() throws Exception {
        Long id = 1L;
        Repository repository = new Repository();
        repository.setId(id);

        when(repositoryService.getById(eq(id))).thenReturn(Optional.of(repository));

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

        when(repositoryService.create(any(Repository.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(repository);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/repositories")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(repositoryService, times(1)).create(any(Repository.class));
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

        when(repositoryService.update(id , request)).thenReturn(updated);
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
        Long ownerId = 1L;
        Repository repository = new Repository();
        List<Repository> repositories = List.of(repository);

        when(repositoryService.findAllByOwnerId(anyLong(), eq(true))).thenReturn(repositories);

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/owner-id/{owner-id}", ownerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(repositoryService, Mockito.times(1)).findAllByOwnerId(Mockito.anyLong(), true);
    }
    @Test
    void getRepositoryByRootFullNameTest() throws Exception {
        String rootFullName = "testRootFullName";
        Repository repository = new Repository();

        Mockito.when(repositoryService.findByRootFullName(Mockito.anyString())).thenReturn(Optional.of(repository));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/root-full-name/{rootFullName}", rootFullName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(repositoryService, Mockito.times(1)).findByRootFullName(Mockito.anyString());;
    }

    @Test
    void getRepositoriesByFilterTest() throws Exception {
        Repository filter = new Repository();
        Repository repository = new Repository();
        List<Repository> repositories = List.of(repository);
        Page<Repository> page = new PageImpl<>(repositories);

        Mockito.when(repositoryService.findAllByFilter(Mockito.any(Repository.class), Mockito.any(Pageable.class), eq(true))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/repositories")
                        .param("name", filter.getDisplayName())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(repositoryService, Mockito.times(1)).findAllByFilter(Mockito.any(Repository.class), Mockito.any(Pageable.class), eq(true));
    }
    }