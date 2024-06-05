package com.glos.filemanagerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.facade.RepositoryApiFacade;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepositoryController.class)
@ExtendWith(MockitoExtension.class)
class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RepositoryClient repositoryClient;
    @MockBean
    private RepositoryRequestMapper requestMapper;
    @MockBean
    private RepositoryDTOMapper repositoryDTOMapper;
    @MockBean
    private RepositoryApiFacade repositoryApiFacade;

    @Test
    void getByIdTest() throws Exception {
            Long id = 1L;
            Repository repositoryDTO = new Repository();
            when(repositoryClient.getRepositoryById(anyLong())).thenReturn(ResponseEntity.ok(repositoryDTO));

            mockMvc.perform(MockMvcRequestBuilders.get("/repositories/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }

    @Test
    void createTest() throws Exception {
        Repository repository = new Repository();
        Repository created = new Repository();
        created.setId(1L);

        when(repositoryClient.createRepository(any(Repository.class))).thenReturn(ResponseEntity.ok(created));

        String repositoryJson = new ObjectMapper().writeValueAsString(repository);

        mockMvc.perform(MockMvcRequestBuilders.post("/repositories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(repositoryJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(created.getId()));
    }

    @Test
    void updateTest()throws Exception {
        Long id = 1L;
        Repository repository = new Repository();
        String repositoryJson = new ObjectMapper().writeValueAsString(repository);

        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(repositoryJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTest()throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/repositories/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByOwnerIdTest() throws Exception{
        Page<RepositoryDTO> page = new Page<>();
        page.setContent(Collections.singletonList(new RepositoryDTO()));
        when(repositoryApiFacade.getRepositoryByOwnerId(1L, 0, 10, "id,asc")).thenReturn(ResponseEntity.ok(page));

        Long id = 1L;
        int pageNum = 0;
        int size = 10;
        String sort = "id,asc";

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/owner-id/" + id)
                        .param("page", String.valueOf(pageNum))
                        .param("size", String.valueOf(size))
                        .param("sort", sort))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void getRepositoryByRootFullNameTest() throws Exception{
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(1L);
        when(repositoryClient.getRepositoryByRootFullName(any())).thenReturn(ResponseEntity.ok(repositoryDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/root-fullname/testRoot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getByFilterTest() throws Exception {
        Page<RepositoryDTO> page = new Page<>();
        page.setContent(Collections.singletonList(new RepositoryDTO()));

        when(repositoryApiFacade.getRepositoryByFilter(any(Repository.class), anyInt(), anyInt(), anyString()))
                .thenReturn(ResponseEntity.ok(page));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}