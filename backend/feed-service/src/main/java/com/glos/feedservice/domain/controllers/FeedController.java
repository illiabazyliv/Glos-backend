package com.glos.feedservice.domain.controllers;

import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController
{
    private List<FeedElementDTO> FeedDTOList;

    public FeedController() {
        FeedDTOList = exampleList();
    }

    @GetMapping("/test")
    public ResponseEntity<List<FeedElementDTO>> getPublicRepos()
    {
        //TODO отримати список із repository

        return ResponseEntity.ok(FeedDTOList);
    }

    private List<FeedElementDTO> exampleList() {
        List<FeedElementDTO> list = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setEmail("email1@gmail.com");
        user.setGender("gender");
        user.setFirst_name("first1");
        user.setLast_name("last1");
        user.setGroups(List.of());
        user.setRoles(List.of());
        list.add(new FeedElementDTO(new RepositoryDTO(
                1L,
                "$~/dir1",
                "repository1",
                "$~/dir1$example1",
                user,
                "user1/dir1",
                "example1",
                "user1/dir1/example1",
                "desc",
                List.of(),
                List.of()
        )));
        return list;
    }
}
