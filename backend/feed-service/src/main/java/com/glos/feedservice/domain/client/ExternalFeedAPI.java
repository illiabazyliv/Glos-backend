package com.glos.feedservice.domain.client;

import com.glos.feedservice.domain.entities.Repository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.SpringQueryMap;

import java.util.List;
import java.util.Map;

@FeignClient(name = "feed")
public interface ExternalFeedAPI {
    @GetMapping
    List<Repository> getPublicRepos (@SpringQueryMap Map<String , Object> filter);
}
