package com.glos.filemanagerservice.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileRequest
{
    public static class FileNode
    {
        private String fileBody;
        private MultipartFile fileData;

        public FileNode(String fileBody, MultipartFile fileData) {
            this.fileBody = fileBody;
            this.fileData = fileData;
        }

        public FileNode() {
        }

        public String getFileBody() {
            return fileBody;
        }

        public void setFileBody(String fileBody) {
            this.fileBody = fileBody;
        }

        public MultipartFile getFileData() {
            return fileData;
        }

        public void setFileData(MultipartFile fileData) {
            this.fileData = fileData;
        }
    }

    public FileRequest() {
    }

    private List<FileNode> fileNodes;

    public FileRequest(List<FileNode> fileNodes) {
        this.fileNodes = fileNodes;
    }

    public List<FileNode> getFileNodes() {
        return fileNodes;
    }

    public void setFileNodes(List<FileNode> fileNodes) {
        this.fileNodes = fileNodes;
    }

    public FileRequest() {
    }
}
