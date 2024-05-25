package com.glos.accessservice.controllers;

import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.accessservice.responseDTO.AccessTypesRequestFilter;
import com.glos.accessservice.responseDTO.Page;
import com.glos.api.entities.AccessType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@WebMvcTest(AccessTypeController.class)
@ExtendWith(MockitoExtension.class)
class AccessTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessTypeApiClient accessTypeApiClient;

    @Test
    public void testGetById() throws Exception {
        AccessType accessType = new AccessType();
        accessType.setId(1L);
        accessType.setName("Test Access Type");

        when(accessTypeApiClient.getById(1L)).thenReturn(ResponseEntity.ok(accessType));

        mockMvc.perform(get("/access-types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Access Type"));
    }

    @Test
    public void testGetByName() throws Exception {
        AccessType accessType = new AccessType();
        accessType.setId(1L);
        accessType.setName("Test Access Type");

        when(accessTypeApiClient.getByName("Test Access Type")).thenReturn(ResponseEntity.ok(accessType));

        mockMvc.perform(get("/access-types/name/Test Access Type"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Access Type"));
    }

    @Test
    public void testGetByFilter() throws Exception {
        AccessTypesRequestFilter filter = new AccessTypesRequestFilter();
        Page<AccessType> page = new Page<>();
        page.setContent(Collections.singletonList(new AccessType()));
        page.setTotalElements(1);
        page.setSize(10);
        page.setNumber(0);

        when(accessTypeApiClient.getByFilter(ArgumentMatchers.any(Map.class))).thenReturn(ResponseEntity.ok(page));

        mockMvc.perform(get("/access-types")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.number").value(0));
    }
}