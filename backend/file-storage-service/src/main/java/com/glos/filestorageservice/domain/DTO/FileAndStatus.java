package com.glos.filestorageservice.domain.DTO;

public class FileAndStatus {

    private String filename;
    private FileOperationStatus status;

    public FileAndStatus() {
    }

    public FileAndStatus(String filename, FileOperationStatus status) {
        this.filename = filename;
        this.status = status;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FileOperationStatus getStatus() {
        return status;
    }

    public void setStatus(FileOperationStatus status) {
        this.status = status;
    }
}
