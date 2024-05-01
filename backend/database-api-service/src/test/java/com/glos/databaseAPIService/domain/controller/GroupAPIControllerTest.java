package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.Comment;
import com.glos.api.entities.Group;
import com.glos.databaseAPIService.domain.filters.GroupFilter;
import com.glos.databaseAPIService.domain.service.GroupService;
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

@WebMvcTest(GroupAPIController.class)
@ExtendWith(MockitoExtension.class)
class GroupAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService groupService;
    @Test
    void getGroupById() throws Exception {
        Long id = 1L;
        Group group = new Group();

        when(groupService.getById(id)).thenReturn(Optional.of(group));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/{id}",id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(groupService, times(1)).getById(id);
    }

    @Test
    void createGroup() throws Exception {
        Group request = new Group();
        Group created = new Group();
        created.setId(1L);

        when(groupService.create(ArgumentMatchers.any(Group.class))).thenReturn(created);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(groupService, times(1)).create(ArgumentMatchers.any(Group.class));
    }

    @Test
    void deleteGroup() throws Exception{
        Long id = 1L;
        doNothing().when(groupService).deleteById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/groups/{id}" , id))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateGroup() throws Exception {
        Long id = 1L;
        Group request = new Group();
        Group updated = new Group();
        updated.setId(id);
        when(groupService.update(id, request)).thenReturn(updated);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/groups/{id}" , id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    void getAllGroups() throws  Exception {
        Group group = new Group();
        group.setId(1L);
        List<Group> groups = Collections.singletonList(group);
        when(groupService.getAll()).thenReturn(groups);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(groups);
        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getGroupsByFilters() throws Exception {
        Group group = new Group();
        group.setId(1L);
        List<Group> groups = Collections.singletonList(group);

        when(groupService.getAll(ArgumentMatchers.any(GroupFilter.class)))
                .thenReturn(groups);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/filter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(groupService, times(1))
                .getAll(ArgumentMatchers.any(GroupFilter.class));
    }
}