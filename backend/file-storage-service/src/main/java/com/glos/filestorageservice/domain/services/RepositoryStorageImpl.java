package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.MoveRequest;
import com.glos.filestorageservice.domain.DTO.RepositoryAndStatus;
import com.glos.filestorageservice.domain.DTO.RepositoryOperationStatus;
import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
    public List<RepositoryAndStatus> create(String rootFullName)
    {
        List<RepositoryAndStatus> repositoryAndStatuses = new ArrayList<>();
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
            repositoryAndStatuses.add(new RepositoryAndStatus(rootFullName, RepositoryOperationStatus.CREATED, "Repository created successfully"));
        }
        catch (Exception e)
        {
            logger.info("Failed to create repository");
            repositoryAndStatuses.add(new RepositoryAndStatus(rootFullName, RepositoryOperationStatus.FAILED, "Failed to create repository " + e.getMessage()));
        }
        return repositoryAndStatuses;
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

//    @Override
//    public List<RepositoryAndStatus> rename(String rootFullName, String newName)
//    {
//
//        List<RepositoryAndStatus> repositoryAndStatuses = new ArrayList<>();
//        try {
//            //TODO поки так, імплементувати парсер
//            if (!rootFullName.endsWith("/")) rootFullName += "/";
//            if (!newName.endsWith("/")) newName += "/";
//
//            Iterable<Result<Item>> resultsList = minioClient.listObjects(
//                    ListObjectsArgs.builder()
//                            .bucket(bucket)
//                            .prefix(rootFullName)
//                            .recursive(true)
//                            .build()
//            );
//
//            for (Result<Item> result : resultsList) {
//                Item item = result.get();
//                String oldObjectName = item.objectName();
//                String newObjectName = newName + oldObjectName.substring(rootFullName.length());
//
//                minioClient.copyObject(
//                        CopyObjectArgs.builder()
//                                .bucket(bucket)
//                                .object(newObjectName)
//                                .source(CopySource.builder()
//                                        .bucket(bucket)
//                                        .object(oldObjectName)
//                                        .build())
//                                .build()
//                );
//
//                minioClient.removeObject(
//                        RemoveObjectArgs.builder()
//                                .bucket(bucket)
//                                .object(oldObjectName)
//                                .build()
//                );
//            }
//
//            minioClient.removeObject(
//                    RemoveObjectArgs.builder()
//                            .bucket(bucket)
//                            .object(rootFullName)
//                            .build()
//            );
//            logger.info("Repository renamed successfully");
//            repositoryAndStatuses.add(new RepositoryAndStatus(rootFullName, RepositoryOperationStatus.RENAMED, "Repository renamed successfully"));
//        }
//        catch (Exception e)
//        {
//            logger.info("Failed to rename repository");
//            repositoryAndStatuses.add(new RepositoryAndStatus(rootFullName, RepositoryOperationStatus.FAILED, "Failed to rename repository " + e.getMessage()));
//
//        }
//        return repositoryAndStatuses;
//    }

    @Override
    public List<RepositoryAndStatus> move(List<MoveRequest.MoveNode> moves) throws Exception
    {
        List<RepositoryAndStatus> repositoryAndStatuses = new ArrayList<>();

        for (var move:moves)
        {
            try {
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
                    String destinationObject = to + "/" + sourceObject.substring(from.length());

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
                repositoryAndStatuses.add(new RepositoryAndStatus("From " + move.getFrom() + " \nto " + move.getTo(), RepositoryOperationStatus.MOVED, "Repository moved successfully"));

            }
            catch (Exception e)
            {
                logger.info("Failed to move repository");
                repositoryAndStatuses.add(new RepositoryAndStatus(move.getFrom(), RepositoryOperationStatus.FAILED, "Failed to move repository"));
            }
        }
        return repositoryAndStatuses;
    }

    @Override
    public List<RepositoryAndStatus> delete(String rootFullName)
    {
        List<RepositoryAndStatus> repositoryAndStatuses = new ArrayList<>();
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
            repositoryAndStatuses.add(new RepositoryAndStatus(rootFullName, RepositoryOperationStatus.DELETED, "Repository deleted successfully"));
        }
        catch (Exception e)
        {
            logger.info("Failed to delete repository");
            repositoryAndStatuses.add(new RepositoryAndStatus(rootFullName, RepositoryOperationStatus.FAILED, "Failed to delete repository"));
        }
        return repositoryAndStatuses;
    }
}
