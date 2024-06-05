package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.BucketOperationStatus;
import com.glos.filestorageservice.domain.DTO.UserBucketAndStatus;
import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserBucketServiceImpl implements UserBucketService
{

    @Autowired
    private  MinioClient minioClient;
    private static final Logger logger = Logger.getLogger("UserBucketServiceImpl");
    @Override
    public UserBucketAndStatus createUserBucket(String username)
    {
        logger.info("Creating bucket " + username);
        UserBucketAndStatus userBucketAndStatus = new UserBucketAndStatus();
        try
        {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(username).build()))
            {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(username).build());
                userBucketAndStatus.setUsername(username);
                userBucketAndStatus.setStatus(BucketOperationStatus.CREATED);
                userBucketAndStatus.setMessage("Bucket created successfully");
                logger.info("Bucket " + username + " created successfully");
            }
            else
            {
                userBucketAndStatus.setUsername(username);
                userBucketAndStatus.setStatus(BucketOperationStatus.ALREADY_EXISTS);
                userBucketAndStatus.setMessage("Bucket already exists");
            }
        }
        catch (Exception e)
        {
            userBucketAndStatus.setUsername(username);
            userBucketAndStatus.setStatus(BucketOperationStatus.FAILED);
            userBucketAndStatus.setMessage("Failed to create bucket " + username);
            throw new RuntimeException(e.getMessage());
        }
        return userBucketAndStatus;
    }

    @Override
    public UserBucketAndStatus deleteUserBucket(String username)
    {
        logger.info("Deleting bucket " + username);
        UserBucketAndStatus userBucketAndStatus = new UserBucketAndStatus();

        try
        {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(username).build()))
            {
                Iterable<Result<Item>> results = minioClient.listObjects(
                        ListObjectsArgs.builder().bucket(username).recursive(true).build()
                );

                for (Result<Item> result : results)
                {
                    Item item = result.get();
                    minioClient.removeObject(
                            RemoveObjectArgs.builder().bucket(username).object(item.objectName()).build()
                    );
                }

                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(username).build());

                userBucketAndStatus.setUsername(username);
                userBucketAndStatus.setStatus(BucketOperationStatus.DELETED);
                userBucketAndStatus.setMessage("Bucket deleted successfully");
                logger.info("Bucket " + username + " deleted successfully");
            }
            else
            {
                userBucketAndStatus.setUsername(username);
                userBucketAndStatus.setStatus(BucketOperationStatus.DOES_NOT_EXIST);
                userBucketAndStatus.setMessage("Bucket does not exist");
            }
        }
        catch (Exception e)
        {
            userBucketAndStatus.setUsername(username);
            userBucketAndStatus.setStatus(BucketOperationStatus.FAILED);
            userBucketAndStatus.setMessage("Failed to delete bucket " + username);
            throw new RuntimeException(e.getMessage());
        }

        return userBucketAndStatus;
    }

    @Override
    public UserBucketAndStatus updateUserBucket(String oldUsername, String newUsername)
    {
        logger.info("Renaming bucket " + oldUsername + " to " + newUsername);
        UserBucketAndStatus userBucketAndStatus = new UserBucketAndStatus();

        try
        {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(oldUsername).build()))
            {
                createUserBucket(newUsername);

                Iterable<Result<Item>> results = minioClient.listObjects(
                        ListObjectsArgs.builder().bucket(oldUsername).recursive(true).build()
                );

                for (Result<Item> result : results)
                {
                    Item item = result.get();
                    minioClient.copyObject(
                            CopyObjectArgs.builder()
                                    .bucket(newUsername)
                                    .object(item.objectName())
                                    .source(CopySource.builder().bucket(oldUsername).object(item.objectName()).build())
                                    .build()
                    );
                }

                deleteUserBucket(oldUsername);
                userBucketAndStatus.setUsername(newUsername);
                userBucketAndStatus.setStatus(BucketOperationStatus.UPDATED);
                userBucketAndStatus.setMessage("Bucket renamed from " + oldUsername + " to " + newUsername + " successfully");
            }
            else
            {
                userBucketAndStatus.setUsername(oldUsername);
                userBucketAndStatus.setStatus(BucketOperationStatus.DOES_NOT_EXIST);
                userBucketAndStatus.setMessage("Bucket does not exist");
            }
        }
        catch (Exception e)
        {
            userBucketAndStatus.setUsername(oldUsername);
            userBucketAndStatus.setStatus(BucketOperationStatus.FAILED);
            userBucketAndStatus.setMessage("Failed to update bucket " + oldUsername);
            throw new RuntimeException(e.getMessage());
        }

        return userBucketAndStatus;
    }
}
