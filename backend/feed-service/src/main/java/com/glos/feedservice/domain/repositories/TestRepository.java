package com.glos.feedservice.domain.repositories;

import com.glos.feedservice.domain.DTO.FeedElementDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.entities.Repository;
import com.glos.feedservice.domain.entities.User;
import com.glos.feedservice.domain.filters.RepositoryFilter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
public class TestRepository
{
    public List<User> getUsers()
    {
        List<FeedElementDTO> feedElementDTOList = new ArrayList<>();
        List<User> repositoryDTOList = new ArrayList<>();
        String url = "http://localhost:8080/api/v1/users";

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);


        ResponseEntity<List<User>> response = restTemplate.exchange(
                builder.toUriString(),
                GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        List<User> users = response.getBody();

        return users;
    }
}
