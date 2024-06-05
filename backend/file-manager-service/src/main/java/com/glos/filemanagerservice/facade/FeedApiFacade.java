package com.glos.filemanagerservice.facade;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.entities.AccessType;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import com.glos.filemanagerservice.responseMappers.AccessModelMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
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
    private final AccessModelMapper accessModelMapper;
    private final RepositoryDTOMapper repositoryDTOMapper;

    public FeedApiFacade(
            RepositoryClient repositoryClient,
            RepositoryRequestMapper requestMapper,
            AccessModelMapper accessModelMapper,
            RepositoryDTOMapper repositoryDTOMapper) {
        this.repositoryClient = repositoryClient;
        this.requestMapper = requestMapper;
        this.accessModelMapper = accessModelMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
    }

    public ResponseEntity<Page<RepositoryDTO>> getPublicRepos(int page, int size, String sort)
    {
        AccessType publicR = new AccessType(1L, "RW_USER_^");
        AccessType piblicRW = new AccessType(4L, "R_GROUP_^");

        List<AccessType> accessTypes = new ArrayList<>();
        accessTypes.add(publicR);
        accessTypes.add(piblicRW);
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        RepositoryRequestFilter requestFilter = requestMapper.toDto(repositoryDTO);

        Map<String, Object> map = MapUtils.map(requestFilter);

        Page<Repository> repositoryPage = repositoryClient.getRepositoriesByFilter(map).getBody();

        return ResponseEntity.ok(repositoryPage.map(repositoryDTOMapper::toDto));
    }
}
