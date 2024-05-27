package com.glos.filestorageservice.domain.services;

import com.glos.filestorageservice.domain.DTO.FileAndStatus;
import com.glos.filestorageservice.domain.DTO.FileWithPath;
import com.glos.filestorageservice.domain.DTO.MoveRequest;

import java.util.List;

public interface FileStorageService {

    List<FileAndStatus> upload(List<FileWithPath> files);

    List<FileWithPath> download(List<String> filenames);

    List<FileAndStatus> update(List<FileWithPath> files);

    List<FileAndStatus> move(List<MoveRequest.MoveNode> moves);

    List<FileAndStatus> delete(List<String> filenames);

}
