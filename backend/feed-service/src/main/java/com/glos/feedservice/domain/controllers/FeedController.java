package com.glos.feedservice.domain.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.entities.AccessType;
import com.glos.feedservice.domain.entities.Repository;
import com.glos.feedservice.domain.entities.User;
import com.glos.feedservice.domain.entityMappers.RepositoryDTOMapper;
import com.glos.feedservice.domain.filters.RepositoryFilter;
import com.glos.feedservice.domain.repositories.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/feed")
public class FeedController
{

    private final FeedRepository feedRepository;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private List<FeedElementDTO> FeedDTOList;

    @Autowired
    public FeedController(FeedRepository feedRepository,
                          RepositoryDTOMapper repositoryDTOMapper,
                          List<FeedElementDTO> feedDTOList) {
        this.feedRepository = feedRepository;
        this.repositoryDTOMapper = repositoryDTOMapper;
        FeedDTOList = feedDTOList;
    }

    @GetMapping
    public ResponseEntity<List<FeedElementDTO>> getPublicRepos(@ModelAttribute RepositoryFilter filter)
    {
        List<RepositoryDTO> repositories = feedRepository.getPublicRepos(filter);
        List<FeedElementDTO> feedElements = new ArrayList<>(repositories.size());
        for (RepositoryDTO repository : repositories)
        {
            FeedElementDTO feedElementDTO = new FeedElementDTO();
            feedElementDTO.setRepository(repository);
            feedElements.add(feedElementDTO);
        }

        return ResponseEntity.ok(feedElements);
    }
}
