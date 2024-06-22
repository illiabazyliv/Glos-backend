package com.glos.filemanagerservice.DTO;

import com.glos.filemanagerservice.entities.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileRequest
{
    public static class FileNode
    {
        private String fileData;
        private MultipartFile file;

        public FileNode() {
        }

        public FileNode(String fileData, MultipartFile file) {
            this.fileData = fileData;
            this.file = file;
        }


        public String getFileData() {
            return fileData;
        }

        public void setFileData(String fileData) {
            this.fileData = fileData;
        }

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }
    }


    private List<FileNode> files;

    public FileRequest() {
    }

    public FileRequest(List<FileNode> files) {
        this.files = files;
    }

    public List<FileNode> getFiles() {
        return files;
    }

    public void setFiles(List<FileNode> files) {
        this.files = files;
    }
}
