package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.MoveRequest;
import com.glos.filestorageservice.domain.DTO.RepositoryOperationStatus;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class RepositoryStorageImpl implements RepositoryStorageService
{
    private static final Logger logger = Logger.getLogger("RepositoryStorageServiceImpl");
    @Autowired
    private MinioClient minioClient;

    private static final String bucket = "test";

    @Override
    public RepositoryOperationStatus create(String rootFullName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        try
        {
            if (!rootFullName.endsWith("/"))
            {
                rootFullName += "/";
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(rootFullName + ".placeholder")
                            .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                            .build()
            );
            logger.info("Repository created successfully");
            return RepositoryOperationStatus.CREATED;
        }
        catch (Exception e)
        {
            logger.info("Failed to create repository");
            return RepositoryOperationStatus.FAILED;
        }
    }

    @Override
    public Map<String, Object> download(String rootFullName) throws Exception
    {
        Map<String, Object> filesAndNames = new HashMap<>();
        try
        {
            if (!rootFullName.endsWith("/")) {
                rootFullName += "/";
            }

            Iterable<Result<Item>> resultsList = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucket)
                            .prefix(rootFullName)
                            .recursive(true)
                            .build()
            );

            for (Result<Item> result : resultsList) {
                Item item = result.get();
                String objectName = item.objectName();

                try (InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName)
                                .build())) {
                    byte[] content = stream.readAllBytes();
                    filesAndNames.put(objectName, content);
                }
            }
            logger.info("Repository downloaded successfully");
            return filesAndNames;
        }
        catch (Exception e)
        {
            logger.info("Failed to download repository");
        }
        return null;
    }

    @Override
    public RepositoryOperationStatus rename(String rootFullName, String newName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try {
            //TODO поки так, імплементувати парсер
            if (!rootFullName.endsWith("/")) rootFullName += "/";
            if (!newName.endsWith("/")) newName += "/";

            Iterable<Result<Item>> resultsList = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucket)
                            .prefix(rootFullName)
                            .recursive(true)
                            .build()
            );

            for (Result<Item> result : resultsList) {
                Item item = result.get();
                String oldObjectName = item.objectName();
                String newObjectName = newName + oldObjectName.substring(rootFullName.length());

                minioClient.copyObject(
                        CopyObjectArgs.builder()
                                .bucket(bucket)
                                .object(newObjectName)
                                .source(CopySource.builder()
                                        .bucket(bucket)
                                        .object(oldObjectName)
                                        .build())
                                .build()
                );

                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucket)
                                .object(oldObjectName)
                                .build()
                );
            }

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(rootFullName)
                            .build()
            );
            logger.info("Repository renamed successfully");
            return RepositoryOperationStatus.RENAMED;
        }
        catch (Exception e)
        {
            logger.info("Failed to rename repository");
            return RepositoryOperationStatus.FAILED;
        }
    }

    @Override
    public RepositoryOperationStatus move(List<MoveRequest.MoveNode> moves) throws Exception
    {
        try
        {
            for (var move:moves)
            {
                String from = move.getFrom();
                String to = move.getTo();

                if (!from.endsWith("/"))
                {
                    from += "/";
                }
                if (!to.endsWith("/"))
                {
                    to += "/";
                }

                create(to);

                Iterable<Result<Item>> resultsList = minioClient.listObjects(
                        ListObjectsArgs.builder()
                                .bucket(bucket)
                                .prefix(from)
                                .recursive(true)
                                .build()
                );



                for (Result<Item> result : resultsList) {
                    Item item = result.get();
                    String sourceObject = item.objectName();
                    String destinationObject = to + "/" + sourceObject.substring(from.length() + 1);

                    minioClient.copyObject(
                            CopyObjectArgs.builder()
                                    .bucket(bucket)
                                    .object(destinationObject)
                                    .source(CopySource.builder()
                                            .bucket(bucket)
                                            .object(sourceObject)
                                            .build())
                                    .build()
                    );

                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(bucket)
                                    .object(sourceObject)
                                    .build()
                    );
                }

                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucket)
                                .object(to + "/.placeholder")
                                .build()
                );

                logger.info("Repository moved successfully");
                return RepositoryOperationStatus.MOVED;
            }
        }
        catch (Exception e)
        {
            logger.info("Failed to move repository");
            return RepositoryOperationStatus.FAILED;
        }
        return RepositoryOperationStatus.FAILED;
    }

    @Override
    public RepositoryOperationStatus delete(String rootFullName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try
        {
            if (!rootFullName.endsWith("/")) {
                rootFullName += "/";
            }
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(rootFullName)
                            .build()
            );
            logger.info("Repository deleted successfully");
            return RepositoryOperationStatus.DELETED;
        }
        catch (Exception e)
        {
            logger.info("Failed to delete repository");
            return RepositoryOperationStatus.FAILED;
        }
    }
}
