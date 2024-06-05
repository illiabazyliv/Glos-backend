package com.glos.filemanagerservice.clients;

import com.glos.filemanagerservice.DTO.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@FeignClient(name = "fileStorage")
public interface FileStorageClient
{
    @PostMapping("/upload")
    ResponseEntity<List<FileAndStatus>> uploadFiles(@SpringQueryMap Map<String, Object> request);
    @GetMapping("/download")
    ResponseEntity<ByteArrayResource> downloadFile(@RequestBody DownloadRequest request);

    @PutMapping("/update")
    ResponseEntity<FileAndStatus> updateFile(@ModelAttribute FileWithPath request);

    @PostMapping("/move")
    ResponseEntity<List<FileAndStatus>> moveFile(@RequestBody MoveRequest request);

    @DeleteMapping("/delete")
     ResponseEntity<List<FileAndStatus>> deleteFile(@RequestBody DeleteRequest request);

}
