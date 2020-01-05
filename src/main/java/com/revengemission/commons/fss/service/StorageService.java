package com.revengemission.commons.fss.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    String save(Path directoryPath, MultipartFile file, String fileExtension) throws IOException;

    Resource loadAsResource(Path fullPath);
}
