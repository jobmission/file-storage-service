package com.revengemission.commons.fileservice.service.impl;

import com.revengemission.commons.fileservice.common.StorageFileNotFoundException;
import com.revengemission.commons.fileservice.common.StorageException;
import com.revengemission.commons.fileservice.persistence.mapper.FileRecordEntityMapper;
import com.revengemission.commons.fileservice.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService implements StorageService {

    @Autowired
    FileRecordEntityMapper fileRecordEntityMapper;

    @Override
    public void store(Path directoryPath, MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, directoryPath.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Resource loadAsResource(Path fullPath) {
        try {
            Resource resource = new UrlResource(fullPath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + fullPath);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + fullPath, e);
        }
    }
}
