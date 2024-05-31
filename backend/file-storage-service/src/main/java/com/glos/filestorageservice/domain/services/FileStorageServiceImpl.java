package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.FileAndStatus;
import com.glos.filestorageservice.domain.DTO.FileOperationStatus;
import com.glos.filestorageservice.domain.DTO.FileWithPath;
import com.glos.filestorageservice.domain.DTO.MoveRequest;
import com.netflix.discovery.converters.Auto;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.compress.utils.IOUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = Logger.getLogger("FileStorageServiceImpl");

    @Autowired
    private  MinioClient minioClient;


    @Override
    public List<FileAndStatus> upload(List<FileWithPath> files)
    {
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
    public List<byte[]> download(List<String> filenames) throws Exception {
        logger.info("Downloading files");

        List<byte[]> filesData = new ArrayList<>();
        for (String path : filenames)
        {
            try (InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("user3")
                            .object(path)
                            .build())) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                IOUtils.copy(stream, buffer);
                filesData.add(buffer.toByteArray());
            } catch (MinioException e) {
                e.printStackTrace();
                throw new Exception("Error while downloading file: " + path, e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        logger.info("Success downloading files");
        return filesData;
    }

    @Override
    public FileAndStatus update(FileWithPath file) {
        logger.info("Updating files");
        String bucketName = "test";

        FileAndStatus fileAndStatuses;
        try
        {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getFilePath())
                            .build()
            );

                /*
                dir1/test.txt
                dir1/test2.docx
                * */

            //TODO імплементувати парсер
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getFilePath())
                            .stream(file.getFile().getInputStream(), file.getFile().getSize(), -1)
                            .contentType(file.getFile().getContentType())
                            .build()
            );

            fileAndStatuses = new FileAndStatus((file.getFilePath()), FileOperationStatus.UPDATED, "File updated successfully");
        }
        catch (Exception e) {
            logger.info("Failed to update file: " + file.getFilePath());
            fileAndStatuses = new FileAndStatus(file.getFilePath(), FileOperationStatus.FAILED, e.getMessage());
        }

        logger.info("Success updating files");
        return fileAndStatuses;
    }

    @Override
    public List<FileAndStatus> move(List<MoveRequest.MoveNode> moves) throws Exception {
        logger.info("Move files");
        String bucket = "test";
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        for (var move:moves)
        {
            try
            {
                minioClient.copyObject(
                        CopyObjectArgs.builder()
                                .bucket(bucket)
                                .object(move.getTo())
                                .source(CopySource.builder()
                                        .bucket(bucket)
                                        .object(move.getFrom())
                                        .build())
                                .build()
                );

                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucket)
                                .object(move.getFrom())
                                .build()
                );
                fileAndStatuses.add(new FileAndStatus(move.getFrom(), FileOperationStatus.MOVED, "File moved successful"));
            }
            catch (Exception e)
            {
                logger.info("Failed to move file");
                fileAndStatuses.add(new FileAndStatus(move.getFrom(), FileOperationStatus.FAILED, "File to move file"));
            }
        }

        logger.info("Success move files");
        return fileAndStatuses;
    }

    @Override
    public List<FileAndStatus> delete(List<String> filenames) {
        logger.info("Delete files");
        String bucketName = "test";
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        for (String filename:filenames)
        {
            try {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(filename)
                                .build()
                );
                fileAndStatuses.add(new FileAndStatus(filename, FileOperationStatus.DELETED, "File deleted successfully"));

            } catch (Exception e) {
                logger.info("Failed to delete file: " + filename);
                fileAndStatuses.add(new FileAndStatus(filename, FileOperationStatus.FAILED, e.getMessage()));
            }
        }

        logger.info("Success delete files");
        return fileAndStatuses;
    }
}
