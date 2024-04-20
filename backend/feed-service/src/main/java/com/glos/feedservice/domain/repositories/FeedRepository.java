package com.glos.feedservice.domain.repositories;

import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.entities.Repository;
import com.glos.feedservice.domain.filters.RepositoryFilter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Service
public class FeedRepository
{
    public List<Repository> getPublicRepos(RepositoryFilter filter)
    {
        List<FeedElementDTO> feedElementDTOList = new ArrayList<>();
        List<RepositoryDTO> repositoryDTOList = new ArrayList<>();
        String url = "http://localhost:8080/api/v1/repositories";

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id", filter.getId())
                .queryParam("rootPath", filter.getRootPath())
                .queryParam("rootName", filter.getRootName())
                .queryParam("rootFullName", filter.getRootFullName())
                .queryParam("owner", filter.getOwner())
                .queryParam("isDefault", filter.isDefault())
                .queryParam("displayPath", filter.getDisplayPath())
                .queryParam("displayName", filter.getDisplayName())
                .queryParam("displayFullName", filter.getDisplayFullName())
                .queryParam("description", filter.getDescription())
                .queryParam("accessTypes", filter.getAccessTypes())
                .queryParam("comments", filter.getComments())
                .queryParam("secureCodes", filter.getSecureCodes())
                .queryParam("tags", filter.getTags());


        ResponseEntity<List<Repository>> response = restTemplate.exchange(
                builder.toUriString(),
                GET,
                null,
                new ParameterizedTypeReference<List<Repository>>() {}
        );
        List<Repository> repositories = response.getBody();

        return repositories;
    }
}
