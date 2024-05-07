package com.glos.commentservice.domain.client;

import com.glos.commentservice.domain.entities.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "comment")
public interface ExternalCommentApi {

    @PostMapping
    ResponseEntity<Comment> create (@RequestBody Comment comment);

}
