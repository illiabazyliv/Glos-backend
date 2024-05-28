package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.FileAndStatus;
import com.glos.filestorageservice.domain.DTO.FileOperationStatus;
import com.glos.filestorageservice.domain.DTO.FileWithPath;
import com.glos.filestorageservice.domain.DTO.MoveRequest;
import com.glos.filestorageservice.domain.controllers.FileStorageFileController;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = Logger.getLogger("FileStorageServiceImpl");

    @Autowired
    private MinioClient minioClient;

    @Override
    public List<FileAndStatus> upload(List<FileWithPath> files) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bucketName = "test";
        logger.info("Uploading files");
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        for (FileWithPath file : files)
        {
            try {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(file.getFilePath())
                                .stream(file.getFile().getInputStream(), file.getFile().getSize(), -1)
                                .contentType(file.getFile().getContentType())
                                .build()
                );
                fileAndStatuses.add(new FileAndStatus(file.getFilePath(), FileOperationStatus.SAVED, "Successfully saved file"));
            }
            catch (Exception e)
            {
                logger.info("Failed to upload file");
                fileAndStatuses.add(new FileAndStatus(file.getFilePath(), FileOperationStatus.FAILED, "Failed saving file"));
            }
        }

        logger.info("Success upload files");
        return fileAndStatuses;
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
