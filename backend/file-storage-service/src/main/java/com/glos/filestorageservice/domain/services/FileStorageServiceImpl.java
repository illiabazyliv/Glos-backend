package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.*;
import com.pathtools.PathParser;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = Logger.getLogger("FileStorageServiceImpl");

    @Autowired
    private  MinioClient minioClient;


    @Override
    public List<FileAndStatus> upload(List<ByteArrayWithPath> files)
    {
        logger.info("Uploading files");
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();

        for (ByteArrayWithPath file : files)
        {
            try {
                com.pathtools.Path filename = PathParser.getInstance().parse(file.getFilePath());
                MultipartFile multipartFile = new MockMultipartFile(filename.getLast().getSimpleName(),  filename.getLast().getSimpleName(), file.getContentType(), file.getFile());
                com.pathtools.Path path = PathParser.getInstance().parse(file.getFilePath());
                putObject(path, multipartFile, -1);
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
            com.pathtools.Path pathObj = PathParser.getInstance().parse(path);
            try (InputStream stream = getObject(pathObj)) {
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

        com.pathtools.Path path = PathParser.getInstance().parse(file.getFilePath());

        // TODO: реалізувати конвертацію (optional)
        /*
        String format = ((FilePathNode)path.reader().last()).getRootFormat();
        MediaType mediaType = null;
        if (format.equals("txt")) {}
        */

        FileAndStatus fileAndStatuses;
        try
        {
            removeObject(path);
            putObject(path, file.getFile(), -1);
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
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        for (var move:moves)
        {
            try
            {
                com.pathtools.Path fromPath = PathParser.getInstance().parse(move.getFrom());
                com.pathtools.Path toPath = PathParser.getInstance().parse(move.getTo());
                copyObject(fromPath, toPath);
                removeObject(fromPath);
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
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();
        for (String filename:filenames)
        {
            try {
                com.pathtools.Path path = PathParser.getInstance().parse(filename);
                removeObject(path);
                fileAndStatuses.add(new FileAndStatus(filename, FileOperationStatus.DELETED, "File deleted successfully"));
            } catch (Exception e) {
                logger.info("Failed to delete file: " + filename);
                fileAndStatuses.add(new FileAndStatus(filename, FileOperationStatus.FAILED, e.getMessage()));
            }
        }

        logger.info("Success delete files");
        return fileAndStatuses;
    }

    private void putBucket(String bucket) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean existsBucket = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucket)
                .build());
        if (!existsBucket) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build());
        }
    }

    private InputStream getObject(com.pathtools.Path pathObj) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String objectPath = pathObj.createBuilder()
                .removeFirst()
                .build().getSimplePath("/", false);
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(pathObj.reader().first().getSimpleName())
                        .object(objectPath)
                        .build());
    }

    private void removeObject(com.pathtools.Path path) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String objectPath = path.createBuilder()
                .removeFirst()
                .build().getSimplePath("/", false);
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(path.reader().first().getSimpleName())
                        .object(objectPath)
                        .build()
        );
    }
    private void putObject(com.pathtools.Path path, MultipartFile file, int partSize) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String objectPath = path.createBuilder()
                .removeFirst()
                .build().getSimplePath("/", false);
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(path.reader().first().getSimpleName())
                        .object(objectPath)
                        .stream(file.getInputStream(), file.getSize(), partSize)
                        .contentType(file.getContentType())
                        .build()
        );
    }

    private void copyObject(com.pathtools.Path from, com.pathtools.Path to) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String pathObjectTo = to.createBuilder()
                .removeFirst()
                .build().getSimplePath("/", false);
        final String pathObjectFrom = from.createBuilder()
                .removeFirst()
                .build().getSimplePath("/", false);
        minioClient.copyObject(
                CopyObjectArgs.builder()
                        .bucket(to.reader().first().getSimpleName())
                        .object(pathObjectTo)
                        .source(CopySource.builder()
                                .bucket(from.reader().first().getSimpleName())
                                .object(pathObjectFrom)
                                .build())
                        .build()
        );
    }
}
