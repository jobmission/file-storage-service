package com.revengemission.commons.fss.service.impl;

import com.revengemission.commons.fss.common.NewIdWorker;
import com.revengemission.commons.fss.common.StorageException;
import com.revengemission.commons.fss.common.StorageFileNotFoundException;
import com.revengemission.commons.fss.persistence.mapper.FileRecordEntityMapper;
import com.revengemission.commons.fss.service.StorageService;
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

    NewIdWorker idWorker = NewIdWorker.getInstance(1, 1);

    @Override
    public String save(Path directoryPath, MultipartFile file, String fileExtension) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        if (file == null || file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + filename);
        }
        String newFileName = idWorker.nextId() + "." + fileExtension;
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, directoryPath.resolve(newFileName),
                StandardCopyOption.REPLACE_EXISTING);
        }
        return newFileName;
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
