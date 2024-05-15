package com.glos.feedservice.domain.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.PageDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.api.entities.AccessType;
import com.glos.api.entities.Repository;
import com.glos.api.entities.User;
import com.glos.feedservice.domain.entityMappers.RepositoryDTOMapper;
import com.glos.feedservice.domain.filters.RepositoryFilter;
import com.glos.feedservice.domain.repositories.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedRepository feedRepository;


    @Autowired
    public FeedController(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @GetMapping
    public ResponseEntity<PageDTO<FeedElementDTO>> getPublicRepos(@ModelAttribute RepositoryFilter filter,
                                                                  @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                                  @RequestParam(value = "pageSize", defaultValue = "12") Integer pageSize,
                                                                  @RequestParam(value = "sort", defaultValue = "id,asc") String sort) {
//List<RepositoryDTO> repositories = feedRepository.getPublicRepos(filter);
        filter.setPageNumber(pageNumber);
        filter.setPageSize(pageSize);
        PageDTO<RepositoryDTO> page = feedRepository.getStaticRepos(filter);

        List<FeedElementDTO> feedElements = page.getContent().stream()
                .map(FeedElementDTO::new)
                .collect(Collectors.toList());

        PageDTO<FeedElementDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(feedElements);
        pageDTO.setPage(pageNumber);
        pageDTO.setSize(pageSize);
        pageDTO.setSort(sort);
        pageDTO.setTotalSize(pageDTO.getContent().size());

        return ResponseEntity.ok(pageDTO);

    }
}
