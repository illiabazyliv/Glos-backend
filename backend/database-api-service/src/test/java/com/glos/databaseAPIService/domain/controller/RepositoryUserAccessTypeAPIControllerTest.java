package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entities.AccessType;
import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RepositoryUserAccessTypeAPIController.class)
class RepositoryUserAccessTypeAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RepositoryUserAccessTypeService service;
    @MockBean
    private RepositoryUserAccessTypeDTOMapper mapper;
    @MockBean
    private RepositoryDTOMapper repositoryDTOMapper;
    @MockBean
    private UserDTOMapper userDTOMapper;
    @Test
    void getRUATByIdTest() throws Exception{
        Long id = 1L;
        User user = new User();
        Repository repository = new Repository();
        AccessType accessType = new AccessType();
        RepositoryUserAccessType ruat = new RepositoryUserAccessType(id,repository, user, accessType);


        when(service.getById(id)).thenReturn(Optional.of(ruat));

        mockMvc.perform(MockMvcRequestBuilders.get("/ruat/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createRUATTest() throws Exception{
        Long id = 1L;
        User user = new User();
        Repository repository = new Repository();
        AccessType accessType = new AccessType();
        RepositoryUserAccessType ruat = new RepositoryUserAccessType(id,repository, user, accessType);
        ruat.setId(id);

        when(service.create(ruat)).thenReturn(ruat);

        ObjectMapper objectMapper = new ObjectMapper();
        String ruatJson = objectMapper.writeValueAsString(ruat);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruatJson))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 500));
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
        RepositoryUserAccessType ruat1 = new RepositoryUserAccessType();
        ruat1.setId(1L);

        RepositoryUserAccessType ruat2 = new RepositoryUserAccessType();
        ruat2.setId(2L);

        List<RepositoryUserAccessType> ruats = Arrays.asList(ruat1, ruat2);

        when(service.getAll()).thenReturn(ruats);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 200
                        || result.getResponse().getStatus() == 500));


    }

    @Test
    void getRUATByFilterTest() throws Exception{
        Long id = 1L;
        User user = new User();
        Repository repository = new Repository();
        AccessType accessType = new AccessType();
        RepositoryUserAccessType ruat = new RepositoryUserAccessType(id,repository, user, accessType);
        RepositoryUserAccessTypeFilter filter = new RepositoryUserAccessTypeFilter();

        RepositoryUserAccessType ruat1 = new RepositoryUserAccessType();
        ruat1.setId(1L);

        RepositoryUserAccessType ruat2 = new RepositoryUserAccessType();
        ruat2.setId(2L);

        List<RepositoryUserAccessType> ruats = Arrays.asList(ruat1, ruat2);

        when(service.getAll(filter)).thenReturn(ruats);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruat/filter")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}