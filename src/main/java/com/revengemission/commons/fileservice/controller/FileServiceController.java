package com.revengemission.commons.fileservice.controller;

import com.revengemission.commons.fileservice.common.StorageFileNotFoundException;
import com.revengemission.commons.fileservice.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;


@Controller
public class FileServiceController {

    @Value("${storage.location.public}")
    private String publicStorageLocation;

    @Value("${storage.location.private}")
    private String privateStorageLocation;

    @Autowired
    StorageService storageService;

    @GetMapping("/files/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, Principal principal) {

        Resource file = storageService.loadAsResource(Paths.get(privateStorageLocation, filename));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/public/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> servePublicFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(Paths.get(publicStorageLocation, filename));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload/public")
    @ResponseBody
    public Map<String, Object> handleFileUpload(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                Principal principal) {
        Map<String, Object> result = new HashMap<>();
        Set<String> fileNames = new LinkedHashSet<>();
        if (files != null && files.size() > 0) {
            files.forEach(multipartFile -> {
                storageService.store(Paths.get(publicStorageLocation), multipartFile);
                fileNames.add(multipartFile.getOriginalFilename());
            });
        }
        result.put("status", "1");
        result.put("files", fileNames);
        return result;
    }

    @PostMapping("/upload/private")
    @ResponseBody
    public Map<String, Object> handleFileUploadPrivate(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                       Principal principal) {
        Map<String, Object> result = new HashMap<>();
        Set<String> fileNames = new LinkedHashSet<>();
        if (files != null && files.size() > 0) {
            files.forEach(multipartFile -> {
                storageService.store(Paths.get(privateStorageLocation), multipartFile);
                fileNames.add(multipartFile.getOriginalFilename());
            });
        }
        result.put("status", "1");
        result.put("files", fileNames);
        return result;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
