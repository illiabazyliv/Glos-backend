package com.glos.commentservice.domain.repositories;

import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.entities.Comment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpMethod.POST;

@Service
public class CommentRepository
{
    //TODO змінити потім
    private final String serviceURL = "http://localhost:8080/api/v1/comments";

    public void createComment(CommentDTO commentDTO)
    {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceURL);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CommentDTO> requestEntity = new HttpEntity<>(commentDTO, headers);

        ResponseEntity<?> response =restTemplate.exchange(
                builder.toUriString(),
                POST,
                requestEntity,
                new ParameterizedTypeReference<HttpStatus>() {
                }

        );
    }
}
