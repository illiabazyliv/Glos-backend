package com.glos.feedservice.domain.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.entities.Repository;
import com.glos.feedservice.domain.filters.RepositoryFilter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URL;
import java.util.*;

import static org.springframework.http.HttpMethod.GET;
/**
 * 	@author - yablonovskydima
 */
@Service
public class FeedRepository
{

    private final String serviceURL = "http://localhost:8080/api/v1/repositories";

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

}
