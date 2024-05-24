package com.glos.feedservice.domain.repositories;

import com.glos.feedservice.domain.DTO.FileDTO;
import com.glos.feedservice.domain.DTO.PageDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.entities.*;
import com.glos.feedservice.domain.entityMappers.FileDTOMapper;
import com.glos.feedservice.domain.filters.RepositoryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static org.springframework.http.HttpMethod.GET;

/**
 * 	@author - yablonovskydima
 */
@Service
public class FeedRepository
{

    private final String serviceURL = "http://localhost:8080/api/v1/repositories";
    private final FileDTOMapper fileDTOMapper;

    private static List<RepositoryDTO> staticRepos = new ArrayList<>();

    @Autowired
    public FeedRepository(FileDTOMapper fileDTOMapper) {
        this.fileDTOMapper = fileDTOMapper;
    }

    private static List<RepositoryDTO> repos()
    {
        Group groups = new Group();
        Role roles = new Role();
        AccessType accessType = new AccessType();
        Tag tag = new Tag();
        FileDTO file = new FileDTO();

        User user1 = new User(1L,
                "user1",
                null,
                "email",
                "+380123456789",
                "man",
                "firstName1",
                "lastName1",
                null,
                true,
                true,
                true,
                true,
                false,
                List.of(),
                List.of(),
                null,
                null,
                null,
                null,
                null);

        User user2 = new User(2L,
                "user2",
                null,
                "email2@gmail.com",
                "+380123456789",
                "man",
                "firstName2",
                "lastName2",
                null,
                true,
                true,
                true,
                true,
                false,
                List.of(),
                List.of(),
                null,
                null,
                null,
                null,
                null);

        User user3 = new User(3L,
                "user3",
                null,
                "email3@gmail.com",
                "+380123456789",
                "man",
                "firstName3",
                "lastName3",
                null,
                true,
                true,
                true,
                true,
                false,
                List.of(),
                List.of(),
                null,
                null,
                null,
                null,
                null);

        User user5 = new User(5L,
                "user5",
                null,
                "email5@gmail.com",
                "+380123456788",
                "man",
                "firstName3",
                "lastName3",
                null,
                true,
                true,
                true,
                true,
                false,
                List.of(),
                List.of(),
                null,
                null,
                null,
                null,
                null);

        User user4 = new User(4L,
                "user4",
                null,
                "email4@gmail.com",
                "+380133456788",
                "man",
                "firstName4",
                "lastName4",
                null,
                true,
                true,
                true,
                true,
                false,
                List.of(),
                List.of(),
                null,
                null,
                null,
                null,
                null);

        staticRepos.add(new RepositoryDTO(1L,
                "$~/dir1",
                "repository96",
                "$~/dir1$example96",
                user1,
                false,
                "user1/dir1",
                "example96",
                "user1/dir1/example96",
                "desc",
                List.of(accessType),
                List.of(tag),
                List.of(file)
        ));

        staticRepos.add(new RepositoryDTO(2L,
                "$~/dir2",
                "repository97",
                "$~/dir2$example97",
                user2,
                false,
                "user2/dir2",
                "example97",
                "user2/dir2/example97",
                "desc2",
                List.of(accessType),
                List.of(tag),
                List.of(file)
        ));

        staticRepos.add(new RepositoryDTO(3L,
                "$~/dir3",
                "repository98",
                "$~/dir3$example98",
                user3,
                false,
                "user3/dir3",
                "example98",
                "user3/dir3/example98",
                "desc3",
                List.of(accessType),
                List.of(tag),
                List.of(file)
        ));

        staticRepos.add(new RepositoryDTO(4L,
                "$~/dir4",
                "repository99",
                "$~/dir4$example99",
                user5,
                false,
                "user4/dir4",
                "example99",
                "user4/dir4/example99",
                "desc4",
                List.of(accessType),
                List.of(tag),
                List.of(file)
        ));

        staticRepos.add(new RepositoryDTO(5L,
                "$~/dir5",
                "repository100",
                "$~/dir5$example100",
                user4,
                false,
                "user5/dir5",
                "example100",
                "user5/dir5/example100",
                "desc5",
                List.of(accessType),
                List.of(tag),
                List.of(file)
        ));

        return staticRepos;
    }

    static
    {
        staticRepos = repos();
    }

    public List<RepositoryDTO> getPublicRepos(RepositoryFilter filter)
    {
        RestTemplate restTemplate = new RestTemplate();

        String url = createUrl(filter);
        ResponseEntity<List<RepositoryDTO>> responseEntity = restTemplate.exchange(
                url,
                GET,
                null,
                new ParameterizedTypeReference<List<RepositoryDTO>>() {}
        );
        List<RepositoryDTO> repositories = responseEntity.getBody();

        if(repositories == null)
        {
            return Collections.emptyList();
        }

        return repositories;
    }

    public String createUrl(RepositoryFilter filter)
    {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceURL);
        if(filter.getId() != null)
        {
            builder.queryParam("id", filter.getId());
        }
        if(filter.getRootPath() != null)
        {
            builder.queryParam("rootPath", filter.getRootPath());
        }
        if(filter.getRootName() != null)
        {
            builder.queryParam("rootName", filter.getRootName());
        }
        if(filter.getRootFullName() != null)
        {
            builder.queryParam("rootFullName", filter.getRootFullName());
        }
        if(filter.isDefault() != null)
        {
            builder.queryParam("isDefault", filter.isDefault());
        }
        if(filter.getDisplayPath() != null)
        {
            builder.queryParam("displayPath", filter.getDisplayPath());
        }
        if(filter.getDisplayFullName() != null)
        {
            builder.queryParam("displayFullName", filter.getDisplayFullName());
        }
        if(filter.getDescription() != null)
        {
            builder.queryParam("description", filter.getDescription());
        }

        String url = builder.toUriString();
        return url;
    }

    public PageDTO<RepositoryDTO> getStaticRepos(RepositoryFilter filter)
    {
        List<FileDTO> files;

        if(filter.getFiles() != null)
        {
            files = filter.getFiles().stream().map(fileDTOMapper::toDto).toList();
        } else {
            files = new ArrayList<>();
        }

        PageDTO<RepositoryDTO> page = new PageDTO<>();
        page.setSize(filter.getPageSize());
        page.setPage(filter.getPageNumber());
        page.setContent(staticRepos.stream().filter((x) -> {return filter.getId() == null || x.getId().equals(filter.getId());})
                .filter((x) -> {return filter.getRootPath() == null ||  x.getRootPath().equals(filter.getRootPath());})
                .filter((x) -> {return filter.getRootName() == null ||  x.getRootName().equals(filter.getRootName());})
                .filter((x) -> {return filter.getRootFullName() == null ||  x.getRootFullName().equals(filter.getRootFullName());})
                .filter((x) -> {return filter.getOwner() == null ||  x.getOwner().equals(filter.getOwner());})
                .filter((x) -> {return filter.isDefault() == null ||  x.getDefault().equals(filter.isDefault());})
                .filter((x) -> {return filter.getDisplayPath() == null ||  x.getDisplayPath().equals(filter.getDisplayPath());})
                .filter((x) -> {return filter.getDisplayName() == null ||  x.getDisplayName().equals(filter.getDisplayName());})
                .filter((x) -> {return filter.getDisplayFullName() == null ||  x.getDisplayFullName().equals(filter.getDisplayFullName());})
                .filter((x) -> {return filter.getDescription() == null ||  x.getDescription().equals(filter.getDescription());})
                .filter((x) -> {return filter.getAccessTypes() == null ||  x.getAccessTypes().equals(filter.getAccessTypes());})
                .filter((x) -> {return filter.getTags() == null ||  x.getTags().equals(filter.getTags());})
                .toList());

        page.setTotalSize(page.getContent().size());
        return page;
    }

}
