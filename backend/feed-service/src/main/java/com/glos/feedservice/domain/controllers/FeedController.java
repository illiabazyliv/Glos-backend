package com.glos.feedservice.domain.controllers;

import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.entities.AccessType;
import com.glos.feedservice.domain.entities.Repository;
import com.glos.feedservice.domain.entities.User;
import com.glos.feedservice.domain.filters.RepositoryFilter;
import com.glos.feedservice.domain.repositories.FeedRepository;
import com.glos.feedservice.domain.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final FeedRepository feedRepository;
    private final TestRepository testRepository;
    private List<FeedElementDTO> FeedDTOList;

    @Autowired
    public FeedController(FeedRepository feedRepository, TestRepository testRepository) {
        this.feedRepository = feedRepository;
        this.testRepository = testRepository;
        FeedDTOList = exampleList();
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

    @GetMapping
    public ResponseEntity<List<Repository>> getPublicRepos()
    {
        AccessType accessType = new AccessType();
        accessType.setId(1L);
        accessType.setName("PUBLIC_R");

        List<AccessType> accessTypeList = new ArrayList<>();
        accessTypeList.add(accessType);

        RepositoryFilter filter = new RepositoryFilter();
        filter.setId(null);
        filter.setAccessTypes(accessTypeList);
        return ResponseEntity.ok(feedRepository.getPublicRepos(filter));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers()
    {
        return ResponseEntity.ok(testRepository.getUsers());
    }

    @GetMapping("/is-ok")
    public ResponseEntity<?> isOk()
    {
        return ResponseEntity.ok().build();
    }
}
