package com.glos.filemanagerservice.DTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadRequest {
    private List<ByteArrayWithPath> files;

    public UploadRequest() {
    }

    public UploadRequest(List<ByteArrayWithPath> files) {
        this.files = files;
    }

    public List<ByteArrayWithPath> getFiles() {
        return files;
    }

    public void setFiles(List<ByteArrayWithPath> files) {
        this.files = files;
    }

    public Map<String, Object> toMap()
    {
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < files.size(); i++)
        {
            ByteArrayWithPath byteArrayWithPath = files.get(i);
            map.put("files[" + i + "].filePath", byteArrayWithPath.getFilePath());
            map.put("files[" + i + "].file", byteArrayWithPath.getFile());
            map.put("files[" + i + "].contentType", byteArrayWithPath.getContentType());
        }
        return map;
    }
}