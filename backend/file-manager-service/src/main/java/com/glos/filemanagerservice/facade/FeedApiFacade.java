package com.glos.filemanagerservice.facade;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.entities.AccessType;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FeedApiFacade
{
    private final RepositoryClient repositoryClient;
    private final RepositoryRequestMapper requestMapper;


    public FeedApiFacade(RepositoryClient repositoryClient, RepositoryRequestMapper requestMapper) {
        this.repositoryClient = repositoryClient;
        this.requestMapper = requestMapper;
    }

    public ResponseEntity<Page<RepositoryDTO>> getPublicRepos(int page, int size, String sort)
    {
        AccessType publicR = new AccessType(4L, "PUBLIC_R");
        AccessType piblicRW = new AccessType(5L, "PUBLIC_RW");

        List<AccessType> accessTypes = new ArrayList<>();
        accessTypes.add(publicR);
        accessTypes.add(piblicRW);
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setAccessTypes(accessTypes);
        RepositoryRequestFilter requestFilter = requestMapper.toDto(repositoryDTO);

        Map<String, Object> map = MapUtils.map(requestFilter);
        return repositoryClient.getRepositoriesByFilter(map);
    }
}
