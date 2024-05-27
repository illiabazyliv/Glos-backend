package com.glos.filestorageservice.domain.DTO;

import java.util.List;

public class UploadRequest {
    private List<FileWithPath> files;

    public UploadRequest() {
    }

    public UploadRequest(List<FileWithPath> filesWithPath) {
        this.files = filesWithPath;
    }

    public List<FileWithPath> getFiles() {
        return files;
    }

    public void setFiles(List<FileWithPath> filesWithPath) {
        this.files = filesWithPath;
    }
}