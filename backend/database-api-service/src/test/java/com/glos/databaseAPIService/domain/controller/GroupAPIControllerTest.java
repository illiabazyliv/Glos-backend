package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.Comment;
import com.glos.api.entities.Group;
import com.glos.databaseAPIService.domain.filters.GroupFilter;
import com.glos.databaseAPIService.domain.responseDTO.GroupDTO;
import com.glos.databaseAPIService.domain.responseMappers.GroupDTOMapper;
import com.glos.databaseAPIService.domain.service.CommentService;
import com.glos.databaseAPIService.domain.service.GroupService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @MockBean
    private GroupDTOMapper groupDTOMapper;
    @Test
    void getGroupById() throws Exception {
        Long id = 1L;
        Group group = new Group();
        GroupDTO groupDTO = new GroupDTO();

        when(groupService.getById(id)).thenReturn(Optional.of(group));
        when(groupDTOMapper.toDto(group)).thenReturn(groupDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/groups/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(groupDTO)));
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
        GroupDTO groupDTO = new GroupDTO();
        List<Group> groups = Collections.singletonList(group);
        Page<Group> page = new PageImpl<>(groups);

        when(groupService.getPage(any(Pageable.class))).thenReturn(page);
        when(groupDTOMapper.toDto(any(Group.class))).thenReturn(groupDTO);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0]").exists());
    }

    @Test
    void getGroupsByFilters() throws Exception {
        Group group = new Group();
        GroupDTO groupDTO = new GroupDTO();
        List<Group> groups = Collections.singletonList(group);
        Page<Group> page = new PageImpl<>(groups);

        when(groupService.getPageByFilter(any(Group.class), any(Pageable.class))).thenReturn(page);
        when(groupDTOMapper.toDto(any(Group.class))).thenReturn(groupDTO);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/filter")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0]").exists());
    }
}