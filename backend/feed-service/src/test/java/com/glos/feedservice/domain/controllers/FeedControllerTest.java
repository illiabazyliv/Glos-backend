package com.glos.feedservice.domain.controllers;

import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.PageDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.filters.RepositoryFilter;
import com.glos.feedservice.domain.repositories.FeedRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FeedController.class)
class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    FeedRepository feedRepository;

    @Test
    public void getPublicReposTest() throws Exception {
        RepositoryFilter filter = new RepositoryFilter();
        List<RepositoryDTO> repository = new ArrayList<RepositoryDTO>();
        PageDTO<RepositoryDTO> page = new PageDTO();
        page.setContent(repository);
        page.setPage(0);
        page.setSize(12);
        page.setTotalSize(0);
        page.setSort("id,asc");
        filter.setPageNumber(0);
        filter.setPageSize(12);

        FeedElementDTO feedElementDTO = new FeedElementDTO();
        feedElementDTO.setRepository(new RepositoryDTO());
        List<FeedElementDTO> feedElements = Collections.singletonList(feedElementDTO);
        PageDTO<FeedElementDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(feedElements);
        pageDTO.setPage(0);
        pageDTO.setSize(12);
        pageDTO.setTotalSize(0);
        pageDTO.setSort("id,asc");

        when(feedRepository.getStaticRepos(any(RepositoryFilter.class)))
                .thenReturn(page);
        when(feedRepository.getPublicRepos(any(RepositoryFilter.class)))
                .thenReturn(repository);
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper
                .writeValueAsString(pageDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/feed")
                        .param("pageNumber", "0")
                        .param("pageSize", "12")
                        .param("sort", "id,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}