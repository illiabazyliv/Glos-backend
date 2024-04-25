package com.glos.feedservice.domain.controllers;

import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.FullRepositoryDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.entities.AccessType;
import com.glos.feedservice.domain.entities.Repository;
import com.glos.feedservice.domain.entities.User;
import com.glos.feedservice.domain.entityMappers.FullRepositoryDTOMapper;
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
    private final FullRepositoryDTOMapper fullRepositoryDTOMapper;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private List<FeedElementDTO> FeedDTOList;

    @Autowired
    public FeedController(FeedRepository feedRepository,
                          FullRepositoryDTOMapper fullRepositoryDTOMapper,
                          RepositoryDTOMapper repositoryDTOMapper,
                          List<FeedElementDTO> feedDTOList) {
        this.feedRepository = feedRepository;
        this.fullRepositoryDTOMapper = fullRepositoryDTOMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
        FeedDTOList = feedDTOList;
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
    //TODO можливо якось додати логіку розбиття на сторінки, щоб передавати менше даних
    @GetMapping
    public ResponseEntity<?> getPublicRepositories()
    {
        RepositoryFilter filter = new RepositoryFilter();
        List<AccessType> accessTypes = new ArrayList<>();

        accessTypes.add(new AccessType("PUBLIC_R"));
        accessTypes.add(new AccessType("PUBLIC_RW"));
        filter.setAccessTypes(accessTypes);

        List<Repository> repositories = feedRepository.getPublicRepos(filter);
        List<FeedElementDTO> feedElements = new ArrayList<>(repositories.size());
        for (int i = 0; i < repositories.size(); i++)
        {
            RepositoryDTO repositoryDTO = new RepositoryDTO();
            repositoryDTOMapper.transferEntityDto(repositories.get(i), repositoryDTO);
            feedElements.get(i).setRepository(repositoryDTO);
        }

        return ResponseEntity.ok().build();
    }

    //TODO тут таке саме
    @GetMapping("/filter")
    public ResponseEntity<List<FeedElementDTO>> getPublicReposByFilter(@ModelAttribute RepositoryFilter filter)
    {
        List<Repository> repositories = feedRepository.getPublicRepos(filter);
        List<FeedElementDTO> feedElements = new ArrayList<>(repositories.size());
        for (int i = 0; i < repositories.size(); i++)
        {
            RepositoryDTO repositoryDTO = new RepositoryDTO();
            repositoryDTOMapper.transferEntityDto(repositories.get(i), repositoryDTO);
            feedElements.get(i).setRepository(repositoryDTO);
        }

        return ResponseEntity.of(Optional.of(feedElements));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullRepositoryDTO> getFullPublicRepository(@PathVariable("id") Long id)
    {
        Repository repository = feedRepository.getPublicRepoById(id);
        FullRepositoryDTO fullRepository = new FullRepositoryDTO();
        fullRepositoryDTOMapper.transferEntityDto(repository, fullRepository);
        return ResponseEntity.of(Optional.of(fullRepository));
    }
}
