package com.revengemission.commons.fileservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    void store(Path directoryPath, MultipartFile file);

    Resource loadAsResource(Path fullPath);
}
