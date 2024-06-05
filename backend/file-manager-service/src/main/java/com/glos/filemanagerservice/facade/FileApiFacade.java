package com.glos.filemanagerservice.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.clients.FileStorageClient;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.requestFilters.FileRequestFilter;
import com.glos.filemanagerservice.responseMappers.FileDTOMapper;
import com.glos.filemanagerservice.responseMappers.FileRequestMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileApiFacade
{
    private final FileClient fileClient;
    private final RepositoryClient repositoryClient;

    private final FileDTOMapper fileDTOMapper;
    private final FileRequestMapper fileRequestMapper;
    private final FileStorageClient fileStorageClient;

    public FileApiFacade(FileClient fileClient,
                         RepositoryClient repositoryClient,
                         FileDTOMapper fileDTOMapper,
                         FileRequestMapper fileRequestMapper, FileStorageClient fileStorageClient) {
        this.fileClient = fileClient;
        this.repositoryClient = repositoryClient;
        this.fileDTOMapper = fileDTOMapper;
        this.fileRequestMapper = fileRequestMapper;
        this.fileStorageClient = fileStorageClient;
    }

    public ResponseEntity<List<FileDTO>> uploadFiles(List<FileRequest.FileNode> uploadRequests) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        List<File> files = new ArrayList<>();
        List<MultipartFile> filesData = new ArrayList<>();
        objectMapper.registerModule(new JavaTimeModule());
        for (var u : uploadRequests)
        {
            files.add(objectMapper.readValue(u.getFileBody(), File.class));
            filesData.add(u.getFileData());
        }

        if (files.size() != filesData.size())
        {
            throw new RuntimeException("Number of entities and file data must be equal");
        }

        List<FileDTO> fileDTOS = new ArrayList<>();
        UploadRequest request = new UploadRequest();

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
                temp.setCreationDate(LocalDateTime.now());
                FileDTO fileDTO = fileClient.createFile(temp).getBody();
                fileDTOS.add(fileDTO);
                fileWithPaths.get(i).setFilePath(temp.getRootFullName());
                fileWithPaths.get(i).setFile(filesData.get(i));
            }

            List<ByteArrayWithPath> byteArrayWithPaths = new ArrayList<>();
            for (var fileWithPath:fileWithPaths)
            {
                ByteArrayWithPath byteArrayWithPath = new ByteArrayWithPath();
                byteArrayWithPath.setFilePath(fileWithPath.getFilePath());
                byteArrayWithPath.setFile(fileWithPath.getFile().getBytes());
                byteArrayWithPath.setContentType(fileWithPath.getFile().getContentType());
                byteArrayWithPaths.add(byteArrayWithPath);
            }

            request.setFiles(byteArrayWithPaths);

            Map<String, Object> map = MapUtils.map(request);
            //TODO змапити реквест
            List<FileAndStatus> fileAndStatuses = fileStorageClient.uploadFiles(map).getBody();

        }
        catch (Exception e)
        {
            throw  new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(fileDTOS);
    }

    public ResponseEntity<?> update(Long id, File file, MultipartFile fileData)
    {

        try
        {
            file.setUpdateDate(LocalDateTime.now());
            file.setId(id);
            fileClient.updateFile(file, id);
            String oldRootFullName = fileClient.getFileByID(id).getBody().getRootFullName();

            if (file.getRootFullName() != null && !oldRootFullName.equals(file.getRootFullName()))
            {
                FileWithPath fileWithPath = new FileWithPath(oldRootFullName, fileData);
                fileStorageClient.updateFile(fileWithPath);

                MoveRequest moveRequest = new MoveRequest();
                moveRequest.getMoves().add(new MoveRequest.MoveNode(oldRootFullName, file.getRootFullName()));

                fileStorageClient.moveFile(moveRequest);
            }
            else
            {
                FileWithPath fileWithPath = new FileWithPath(oldRootFullName, fileData);
                fileStorageClient.updateFile(fileWithPath);
            }


        }
        catch (Exception e)
        {
            throw  new RuntimeException(e.getMessage());
        }

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> deleteFiles(List<Long> ids)
    {
        List<String> rootFullNames = new ArrayList<>();
        try
        {
            for (Long id:ids)
            {
                rootFullNames.add(fileClient.getFileByID(id).getBody().getRootFullName());
                fileClient.deleteFile(id);
            }

            DeleteRequest request = new DeleteRequest(rootFullNames);
            fileStorageClient.deleteFile(request);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.noContent().build();

    }

    public ResponseEntity<ByteArrayResource> downloadFiles(List<String> rootFullNames)
    {
        DownloadRequest request = new DownloadRequest(rootFullNames);
        return ResponseEntity.ok(fileStorageClient.downloadFile(request).getBody());
    }

    public ResponseEntity<Page<FileDTO>> getFileByRepository(Long repositoryId, int page, int size, String sort)
    {
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryById(repositoryId).getBody();
        FileDTO fileDTO = new FileDTO();
        fileDTO.setRepository(repositoryDTO);
        FileRequestFilter filter = new FileRequestFilter();
        fileRequestMapper.transferEntityDto(fileDTO, filter);
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);

        Map<String, Object> map = MapUtils.map(filter);
        return ResponseEntity.ok(fileClient.getFilesByFilter(map).getBody());
    }

    public ResponseEntity<Page<FileDTO>> getFilesByFilter(File file, int page, int size, String sort)
    {
        FileDTO fileDTO = fileDTOMapper.toDto(file);
        FileRequestFilter filter = fileRequestMapper.toDto(fileDTO);
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);

        Map<String, Object> map = MapUtils.map(filter);
        return ResponseEntity.ok(fileClient.getFilesByFilter(map).getBody());
    }
}
