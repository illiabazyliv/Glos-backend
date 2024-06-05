package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.*;
import com.glos.filestorageservice.domain.services.FileStorageService;
import com.glos.filestorageservice.domain.utils.ZipUtil;
import com.pathtools.Path;
import com.pathtools.PathParser;
import com.pathtools.pathnode.FilePathNode;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class FileStorageFileController
{

    private final FileStorageService fileStorageService;

    public FileStorageFileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<FileAndStatus>> uploadFiles(@ModelAttribute UploadRequest request)
    {
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();

        try
        {
            fileAndStatuses = fileStorageService.upload(request.getFiles());
        }
        catch (Exception e)
        {
             throw new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok(fileAndStatuses);
    }

    @GetMapping("/files/download/{filename}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String filename) {
        try {
            final Path path = PathParser.getInstance().parse(filename);
            final FilePathNode fileNode = (FilePathNode) path.reader().last();

            final List<byte[]> filesData = fileStorageService.download(Collections.singletonList(filename));
            if (filesData.isEmpty())
                return ResponseEntity.notFound().build();

            final byte[] bytes = filesData.get(0);
            final ByteArrayResource resource = new ByteArrayResource(bytes);

            final java.nio.file.Path pathIo = java.nio.file.Path.of(path.getSimplePath("/", true));
            String mimeType = Files.probeContentType(pathIo);

            if (mimeType == null)
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

            final HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileNode.getSimpleName()));
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentLength(bytes.length);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestBody DownloadRequest request)
    {
        try
        {
            List<byte[]> filesData = fileStorageService.download(request.getFilenames());

            List<String> fileNames = request.getFilenames().stream()
                    .map(path -> PathParser.getInstance().parse(path).getLast().getSimpleName())
                    .toList();

            byte[] zipData = ZipUtil.createZip(filesData, fileNames);

            ByteArrayResource resource = new ByteArrayResource(zipData);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=files.zip");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFile(@ModelAttribute FileWithPath request)
    {
        return ResponseEntity.ok(fileStorageService.update(request));
    }

    @PostMapping("/move")
    public ResponseEntity<List<FileAndStatus>> moveFile(@RequestBody MoveRequest request) throws Exception
    {
        return ResponseEntity.ok(fileStorageService.move(request.getMoves()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<FileAndStatus>> deleteFile(@RequestBody DeleteRequest request)
    {
        return ResponseEntity.ok(fileStorageService.delete(request.getFilenames()));
    }
}
