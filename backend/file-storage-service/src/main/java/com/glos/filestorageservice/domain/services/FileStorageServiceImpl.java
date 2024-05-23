package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.FileAndStatus;
import com.glos.filestorageservice.domain.DTO.FileWithPath;
import com.glos.filestorageservice.domain.DTO.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = Logger.getLogger("FileStorageServiceImpl");

    @Override
    public List<FileAndStatus> upload(List<FileWithPath> files) {
        logger.info("Uploading files");
        // TODO: write upload files
        logger.info("Success upload files");
        return List.of();
    }

    @Override
    public List<FileWithPath> download(List<String> filenames) {
        logger.info("Downloading files");
        // TODO: write download files
        logger.info("Success downloading files");
        return List.of();
    }

    @Override
    public List<FileAndStatus> update(List<FileWithPath> files) {
        logger.info("Updating files");
        // TODO: write update files
        logger.info("Success updating files");
        return List.of();
    }

    @Override
    public List<FileAndStatus> move(List<MoveRequest.MoveNode> moves) {
        logger.info("Move files");
        // TODO: write move files
        logger.info("Success move files");
        return List.of();
    }

    @Override
    public List<FileAndStatus> delete(List<String> filenames) {
        logger.info("Delete files");
        // TODO: write delete files
        logger.info("Success delete files");
        return List.of();
    }
}
