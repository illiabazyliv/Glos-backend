package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.FileAndStatus;
import com.glos.filestorageservice.domain.DTO.FileWithPath;
import com.glos.filestorageservice.domain.DTO.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public List<FileAndStatus> upload(List<FileWithPath> files) {
        return List.of();
    }

    @Override
    public List<FileWithPath> download(List<String> filenames) {
        return List.of();
    }

    @Override
    public List<FileAndStatus> update(List<FileWithPath> files) {
        return List.of();
    }

    @Override
    public List<FileAndStatus> move(List<MoveRequest.MoveNode> moves) {
        return List.of();
    }

    @Override
    public List<FileAndStatus> delete(List<String> filenames) {
        return List.of();
    }
}
