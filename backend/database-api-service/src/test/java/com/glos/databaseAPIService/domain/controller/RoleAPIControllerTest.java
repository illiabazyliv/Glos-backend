package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.Role;
import com.glos.databaseAPIService.domain.service.RoleService;
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

@WebMvcTest(RoleAPIController.class)
@ExtendWith(MockitoExtension.class)
class RoleAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoleService roleService;

    @Test
    public void getRoleByIdTest() throws Exception {
        Long id = 1L;
        Role role = new Role();
        role.setId(id);
        when(roleService.getById(id)).thenReturn(Optional.of(role));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(role);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/roles")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(roleService, times(1)).create(any(Role.class));
    }

    @Test
    void createRole() throws Exception{
        Role role = new Role();
        Role response = new Role();
        response.setId(1L);

        when(roleService.create(any(Role.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(role);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/roles")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/v1/roles/"+response.getId()));

        verify(roleService, times(1)).create(any(Role.class));
    }

    @Test
    void deleteRole() throws Exception{
        Long id = 1L;

        doNothing().when(roleService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/roles/{id}", id))
                .andExpect(status().isNoContent());
    }

    //passed
    @Test
    void updateRole() throws Exception{
        Long id =1L;
        Role request = new Role();
        Role updated = new Role();

        when(roleService.update(id, request)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/roles/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }

    //incorrect request
    @Test
    void getRoleByName() throws Exception{
        String roleName = "admin";
        Role role = new Role();
        role.setName(roleName);

        when(roleService.findByName(anyString())).thenReturn(Optional.of(role));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/roles/name/{name}" + roleName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(roleService, times(1)).findByName(roleName);
    }

    @Test
    void getAllRoles() throws Exception{
        Role role = new Role();
        role.setId(1L);
        role.setName("admin");
        List<Role> roles = List.of(role);
        when(roleService.getAll()).thenReturn(roles);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(roles);
        mockMvc.perform(MockMvcRequestBuilders.get("/roles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

}
