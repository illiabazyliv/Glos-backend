package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.FileAndStatus;
import com.glos.filestorageservice.domain.DTO.FileWithPath;
import com.glos.filestorageservice.domain.DTO.MoveRequest;
import io.minio.errors.*;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface FileStorageService {

    List<FileAndStatus> upload(List<FileWithPath> files) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    List<byte[]> download(List<String> filenames) throws Exception;

    List<FileAndStatus> update(List<FileWithPath> files);

    List<FileAndStatus> move(List<MoveRequest.MoveNode> moves) throws Exception;

    List<FileAndStatus> delete(List<String> filenames);

}
