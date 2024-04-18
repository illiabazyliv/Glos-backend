package com.glos.feedservice.domain.controllers;

import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController
{
    private List<FeedElementDTO> FeedDTOList;

    @GetMapping
    public ResponseEntity<List<FeedElementDTO>> getPublicRepos()
    {
        //TODO отримати список із repository

        return ResponseEntity.ok(FeedDTOList);
    }

}
