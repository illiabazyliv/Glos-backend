package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.MoveRequest;
import com.glos.filestorageservice.domain.DTO.RepositoryAndStatus;
import com.glos.filestorageservice.domain.DTO.RepositoryOperationStatus;
import com.pathtools.NodeType;
import com.pathtools.Path;
import com.pathtools.PathParser;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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

    private static final String bucket = "xily1";

    @Override
    public List<RepositoryAndStatus> create(String rootFullName)
    {
        List<RepositoryAndStatus> repositoryAndStatuses = new ArrayList<>();
        try
        {
            Path path = PathParser.getInstance().parse(rootFullName);
            createRepo(path, -1);
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
        Map<String, Object> filesAndNames;
        try
        {
            Path path = PathParser.getInstance().parse(rootFullName);
            filesAndNames = downloadRepo(path);
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
    public List<RepositoryAndStatus> move(List<MoveRequest.MoveNode> moves) throws Exception
    {
        List<RepositoryAndStatus> repositoryAndStatuses = new ArrayList<>();

        for (var move:moves)
        {
            try {
                String from = move.getFrom();
                String to = move.getTo();

                Path fromPath = PathParser.getInstance().parse(from);
                Path toPath = PathParser.getInstance().parse(to);

                moveRepo(fromPath, toPath);

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
    public RepositoryAndStatus delete(String rootFullName)
    {
        RepositoryAndStatus repositoryAndStatuses;
        try
        {
            Path path = PathParser.getInstance().parse(rootFullName);
            deleteRepo(path);
            logger.info("Repository deleted successfully");
            repositoryAndStatuses = new RepositoryAndStatus(rootFullName, RepositoryOperationStatus.DELETED, "Repository deleted successfully");
        }
        catch (Exception e)
        {
            logger.info("Failed to delete repository");
            repositoryAndStatuses = new RepositoryAndStatus(rootFullName, RepositoryOperationStatus.FAILED, "Failed to delete repository");
        }
        return repositoryAndStatuses;
    }

    private void createRepo(com.pathtools.Path path, int partSize) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Path newPath = path.createBuilder().removeFirst().build();
        String simplePath = newPath.getSimplePath("/", false);

        if (!simplePath.endsWith("/"))
        {
            simplePath += "/";
        }

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(path.reader().first().getSimpleName())
                        .object(simplePath + ".placeholder")
                        .stream(new ByteArrayInputStream(new byte[]{}), 0, partSize)
                        .build()
        );
    }

    private  Map<String, Object> downloadRepo(com.pathtools.Path path) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Path newPath = path.createBuilder().removeFirst().build();
        String simplePath = newPath.getSimplePath("/", false);
        Map<String, Object> filesAndNames = new HashMap<>();

        if (!simplePath.endsWith("/"))
        {
            simplePath += "/";
        }

        Iterable<Result<Item>> resultsList = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(path.reader().first().getSimpleName())
                        .prefix(simplePath)
                        .recursive(true)
                        .build()
        );

        for (Result<Item> result : resultsList) {
            Item item = result.get();
            String objectName = item.objectName();

            try (InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(path.reader().first().getSimpleName())
                            .object(objectName)
                            .build())) {
                byte[] content = stream.readAllBytes();
                filesAndNames.put(objectName, content);
            }
        }
        return filesAndNames;
    }

    private void deleteRepo(com.pathtools.Path path) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Path newPath = path.createBuilder().removeFirst().build();
        String simplePath = newPath.getSimplePath("/", false);

        if (!simplePath.endsWith("/"))
        {
            simplePath += "/";
        }

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(path.reader().first().getSimpleName())
                        .prefix(simplePath)
                        .recursive(true).
                        build());

        for (Result<Item> result : results)
        {
            String objectName = result.get().objectName();
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(path.reader().first().getSimpleName())
                            .object(objectName).build());
        }
    }

    private void moveRepo(com.pathtools.Path from, com.pathtools.Path to) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Path newTo = to.createBuilder().removeFirst().build();
        String simpleToPath = newTo.getSimplePath("/", false);

        Path newFrom = from.createBuilder().removeFirst().build();
        String simpleFromPath = newFrom.getSimplePath("/", false);

        if (!simpleFromPath.endsWith("/"))
        {
            simpleFromPath += "/";
        }
        if (!simpleToPath.endsWith("/"))
        {
            simpleToPath += "/";
        }

        createRepo(to, -1);

        Iterable<Result<Item>> resultsList = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(from.reader().first().getSimpleName())
                        .prefix(simpleFromPath)
                        .recursive(true)
                        .build()
        );


        for (Result<Item> result : resultsList) {
            Item item = result.get();
            String sourceObject = item.objectName();
            java.nio.file.Path path = java.nio.file.Paths.get(sourceObject);
            String filename = path.getFileName().toString();
            String destinationObject = simpleToPath + filename;

            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(from.reader().first().getSimpleName())
                            .object(destinationObject)
                            .source(CopySource.builder()
                                    .bucket(to.reader().first().getSimpleName())
                                    .object(sourceObject)
                                    .build())
                            .build()
            );

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(from.reader().first().getSimpleName())
                            .object(sourceObject)
                            .build()
            );
        }
    }
}
