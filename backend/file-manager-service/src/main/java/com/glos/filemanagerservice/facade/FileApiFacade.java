package com.glos.filemanagerservice.facade;

import com.accesstools.AccessNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.clients.FileStorageClient;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.clients.TagClient;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.requestFilters.FileRequestFilter;
import com.glos.filemanagerservice.responseMappers.FileDTOMapper;
import com.glos.filemanagerservice.responseMappers.FileRequestMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileApiFacade
{
    private final FileClient fileClient;
    private final RepositoryClient repositoryClient;

    private final FileDTOMapper fileDTOMapper;
    private final FileRequestMapper fileRequestMapper;
    private final FileStorageClient fileStorageClient;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final TagClient tagClient;

    public FileApiFacade(FileClient fileClient,
                         RepositoryClient repositoryClient,
                         FileDTOMapper fileDTOMapper,
                         FileRequestMapper fileRequestMapper,
                         RepositoryDTOMapper repositoryDTOMapper,
                         FileStorageClient fileStorageClient, TagClient tagClient) {
        this.fileClient = fileClient;
        this.repositoryClient = repositoryClient;
        this.fileDTOMapper = fileDTOMapper;
        this.fileRequestMapper = fileRequestMapper;
        this.fileStorageClient = fileStorageClient;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.tagClient = tagClient;
    }

    public ResponseEntity<List<FileAndStatus>> uploadFiles(List<FileRequest.FileNode> uploadRequests) throws JsonProcessingException
    {
        List<File> files = new ArrayList<>();
        List<MultipartFile> filesData = new ArrayList<>();
        for (var u : uploadRequests)
        {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = objectMapper.readValue(u.getFileData(), File.class);
            files.add(file);
            filesData.add(u.getFile());
        }

        if (files.size() != filesData.size())
        {
            throw new RuntimeException("Number of entities and file data must be equal");
        }

        List<FileAndStatus> fileAndStatuses = new ArrayList<>();

        List<FileWithPath> fileWithPaths = new ArrayList<>();
        for (int i = 0; i < files.size(); i++)
        {
            fileWithPaths.add(new FileWithPath());
        }

        try
        {
            for (int i = 0; i < files.size(); i++)
            {
                File temp = files.get(i);
                checkAccessTypes(temp);
                temp.setCreationDate(LocalDateTime.now());
                fileClient.createFile(temp);
                fileWithPaths.get(i).setFilePath(temp.getRootFullName());
                fileWithPaths.get(i).setFile(filesData.get(i));
            }

//            for (FileWithPath file:fileWithPaths)
//            {
//               fileAndStatuses.add( fileStorageClient.uploadFiles(file.getFilePath(), file.getFile()).getBody());
//            }
        }
        catch (Exception e)
        {
            throw  new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(fileAndStatuses);
    }

    public ResponseEntity<List<FileAndStatus>> update(FileUpdateRequest updateRequest)
    {
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        try
        {
            MoveRequest moveRequest = new MoveRequest();
            for (FileUpdateRequest.FileNode request: updateRequest.getFileNodes())
            {
                ObjectMapper objectMapper = new ObjectMapper();
                File file = objectMapper.readValue(request.getFileBody(), File.class);
                checkAccessTypes(file);
                file.setUpdateDate(LocalDateTime.now());
                file.setId(request.getId());

                String oldRootFullName = fileClient.getFileByID(request.getId()).getBody().getRootFullName();
                fileClient.updateFile(file, request.getId());

                if (file.getRootFullName() != null && !oldRootFullName.equals(file.getRootFullName()))
                {
                    MoveRequest.MoveNode moveNode = new MoveRequest.MoveNode(oldRootFullName, file.getRootFullName());
                    moveRequest.getMoves().add(moveNode);
                }

//                if (request.getFileData() != null)
//                {
//                    FileWithPath fileWithPath = new FileWithPath(oldRootFullName, request.getFileData());
//                    fileAndStatuses.add(fileStorageClient.updateFile(fileWithPath).getBody());
//                }

            }
//            if (moveRequest != null)
//            {
//                fileStorageClient.moveFile(moveRequest);
//            }
        }
        catch (Exception e)
        {
            throw  new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok(fileAndStatuses);
    }

    public ResponseEntity<List<FileAndStatus>> deleteFiles(List<String> rootFullNames)
    {
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        if (rootFullNames == null || rootFullNames.isEmpty())
        {
            return ResponseEntity.ok(List.of());
        }
        try
        {
            for (String rotFullName:rootFullNames)
            {
                fileClient.deleteFile(fileClient.getFileByRootFullName(rotFullName).getBody().getId());
            }

            DeleteRequest request = new DeleteRequest(rootFullNames);
            //fileAndStatuses = fileStorageClient.deleteFile(request).getBody();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(fileAndStatuses);

    }

    public ResponseEntity<ByteArrayResource> downloadFiles(DownloadRequest request)
    {
        ByteArrayResource byteArrayResource = fileStorageClient.downloadFile(request).getBody();
        return ResponseEntity.ok(byteArrayResource);
    }

    public ResponseEntity<Page<FileDTO>> getFileByRepository(Long repositoryId, int page, int size, String sort)
    {
//        ResponseEntity<Repository> response = repositoryClient.getRepositoryById(repositoryId);
//        Repository repository = response.getBody();
//        RepositoryDTO repositoryDTO = repositoryDTOMapper.toDto(repository);
//        FileDTO fileDTO = new FileDTO();
//        fileDTO.setRepository(repositoryDTO);
//        FileRequestFilter filter = new FileRequestFilter();
//        fileRequestMapper.transferEntityDto(fileDTO, filter);
//        filter.setPage(page);
//        filter.setSize(size);
//        filter.setSort(sort);
        Repository repository = new Repository();
        repository.setId(repositoryId);
        File file = new File();
        file.setRepository(repository);

        //Map<String, Object> map = MapUtils.map(filter);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("size", size);
        map.put("sort", sort);
        return ResponseEntity.ok(fileClient.getFilesByFilter(file, map).getBody());
    }

    public ResponseEntity<Page<FileDTO>> getFilesByFilter(File file, int page, int size, String sort)
    {
        checkAccessTypes(file);
        //FileDTO fileDTO = fileDTOMapper.toDto(file);
//        FileRequestFilter filter = fileRequestMapper.toDto(fileDTO);
//        filter.setPage(page);
//        filter.setSize(size);
//        filter.setSort(sort);

//        Map<String, Object> map = MapUtils.map(filter);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("size", size);
        map.put("sort", sort);
        return ResponseEntity.ok(fileClient.getFilesByFilter(file, map).getBody());
    }

    private void checkAccessTypes(File file) {
        if (file != null && file.getAccessTypes() != null) {
            file.setAccessTypes(new HashSet<>(
                    file.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).collect(Collectors.toSet())
            ));
        }
    }
}
