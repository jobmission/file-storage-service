package com.revengemission.commons.fss.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revengemission.commons.fss.common.StorageFileNotFoundException;
import com.revengemission.commons.fss.service.StorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Controller
public class FileStorageController {

    @Value("${storage.location.public}")
    private String publicStorageLocation;

    @Value("${storage.location.private}")
    private String protectedStorageLocation;

    @Value("#{'${fss.type.whitelist}'.split(',')}")
    private Set<String> whitelist;

    @Autowired
    StorageService storageService;

    /**
     * 下载流
     *
     * @param request
     * @param response
     * @param filename
     * @throws IOException
     */
    @RequestMapping(value = "/download/public", method = RequestMethod.GET)
    public void downloadStreamPublic(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "filename") String filename) throws IOException {
        // 防止跨文件夹等任意文件下载
        Path targetPath = Paths.get(publicStorageLocation, filename).normalize();
        Path allowPath = Paths.get(publicStorageLocation).normalize();
        if (targetPath.startsWith(allowPath)) {
            InputStream myStream = Files.newInputStream(targetPath);
            // Copy the stream to the response's output stream.
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } else {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            Map<String, Object> responseMessage = new HashMap<>(16);
            responseMessage.put("status", HttpStatus.FORBIDDEN);
            responseMessage.put("message", "非法请求！");
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(),
                JsonEncoding.UTF8);
            objectMapper.writeValue(jsonGenerator, responseMessage);
        }
    }

    /**
     * 下载文件流
     *
     * @param request
     * @param response
     * @param filename
     * @throws IOException
     */
    @RequestMapping(value = "/download/protected", method = RequestMethod.GET)
    public void downloadStream(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "filename") String filename) throws IOException {

        // 防止跨文件夹等任意文件下载
        Path targetPath = Paths.get(protectedStorageLocation, filename).normalize();
        Path allowPath = Paths.get(protectedStorageLocation).normalize();
        if (targetPath.startsWith(allowPath)) {
            InputStream myStream = Files.newInputStream(targetPath);
            // Copy the stream to the response's output stream.
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } else {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            Map<String, Object> responseMessage = new HashMap<>(16);
            responseMessage.put("status", HttpStatus.FORBIDDEN);
            responseMessage.put("message", "非法请求！");
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(),
                JsonEncoding.UTF8);
            objectMapper.writeValue(jsonGenerator, responseMessage);
        }

    }

    @PostMapping("/upload/protected")
    @ResponseBody
    public Map<String, Object> handleFileUploadProtected(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "sub", required = false) String sub, @RequestParam(value = "originalName", required = false) String originalName) {
        return saveToDisk(file, protectedStorageLocation, sub, originalName);
    }

    @PostMapping(value = "/upload/public")
    @ResponseBody
    public Map<String, Object> handleFileUpload(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "sub", required = false) String sub, @RequestParam(value = "originalName", required = false) String originalName) {
        return saveToDisk(file, publicStorageLocation, sub, originalName);
    }

    private Map<String, Object> saveToDisk(List<MultipartFile> files, String storageLocation) {
        Map<String, Object> result = new HashMap<>(16);
        List<String> fileNames = new LinkedList<>();
        if (files != null && files.size() > 0) {
            files.forEach(multipartFile -> {
                String fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
                if (fileExtension == null) {
                    String multipartFileContentType = multipartFile.getContentType();
                    if (StringUtils.endsWithIgnoreCase("image/png", multipartFileContentType)) {
                        fileExtension = "png";
                    } else if (StringUtils.endsWithIgnoreCase("image/jpg", multipartFileContentType)) {
                        fileExtension = "jpg";
                    } else if (StringUtils.endsWithIgnoreCase("image/jpeg", multipartFileContentType)) {
                        fileExtension = "jpg";
                    }
                }
                if (whitelist.contains(StringUtils.trimAllWhitespace(fileExtension).toLowerCase())) {
                    try {
                        String newFileName = storageService.save(Paths.get(storageLocation), multipartFile, fileExtension);
                        fileNames.add(newFileName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (fileNames.size() > 0) {
            result.put("status", 1);
        } else {
            result.put("status", 0);
        }
        result.put("files", fileNames);
        return result;
    }

    private Map<String, Object> saveToDisk(MultipartFile multipartFile, String storageLocation, String sub, String originalName) {
        if (!StringUtils.isEmpty(sub)) {
            sub = sub.replaceAll("[^a-zA-Z]", "");
        }
        Map<String, Object> result = new HashMap<>(16);
        String newFileName = "";
        String fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        if (fileExtension == null || "".equals(fileExtension)) {
            String multipartFileContentType = multipartFile.getContentType();
            if (StringUtils.endsWithIgnoreCase("image/png", multipartFileContentType)) {
                fileExtension = "png";
            } else if (StringUtils.endsWithIgnoreCase("image/jpg", multipartFileContentType)) {
                fileExtension = "jpg";
            } else if (StringUtils.endsWithIgnoreCase("image/jpeg", multipartFileContentType)) {
                fileExtension = "jpg";
            }
        }
        if (whitelist.contains(StringUtils.trimAllWhitespace(fileExtension).toLowerCase())) {
            try {
                newFileName = storageService.save(Paths.get(storageLocation, sub), multipartFile, fileExtension);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isEmpty(newFileName)) {
            result.put("status", 0);
        } else {
            result.put("status", 1);
            if (StringUtils.isEmpty(sub)) {
                result.put("file", newFileName);
            } else {
                result.put("file", sub + "/" + newFileName);
            }
        }

        return result;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
