package com.glos.filemanagerservice.clients;

import com.glos.filemanagerservice.DTO.*;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient(name = "fileStorage")
public interface FileStorageClient
{
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileAndStatus> uploadFiles(@RequestPart(value = "filePath") String filePath, @RequestPart(value = "file") MultipartFile file);
    @GetMapping("/files/download")
    ResponseEntity<ByteArrayResource> downloadFile(@RequestBody DownloadRequest request);

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileAndStatus> updateFile(@ModelAttribute FileWithPath request);

    @PostMapping("/move")
    ResponseEntity<List<FileAndStatus>> moveFile(@RequestBody MoveRequest request);

    @DeleteMapping("/delete")
     ResponseEntity<List<FileAndStatus>> deleteFile(@RequestBody DeleteRequest request);

}
