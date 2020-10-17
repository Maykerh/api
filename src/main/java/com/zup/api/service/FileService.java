package com.zup.api.service;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

import com.zup.api.error.exception.FileUploadException;

@Service
@Setter
@ConfigurationProperties(prefix = "file")
public class FileService {
    private String uploadDir;

    public String uploadFile(MultipartFile file, String extraPath) {
        try {
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = FilenameUtils.getBaseName(StringUtils.cleanPath(file.getOriginalFilename())) + System.currentTimeMillis();
            String fullPath = FilenameUtils.separatorsToSystem(this.getUploadDir(extraPath));

            this.validateDirectory(fullPath);

            Path fileFullPath = Paths.get(fullPath + File.separator + fileName + '.' + fileExtension);

            Files.copy(file.getInputStream(), fileFullPath.toAbsolutePath().normalize(), StandardCopyOption.REPLACE_EXISTING);

            return fileName + '.' + fileExtension;
        } catch (Exception e) {
            e.printStackTrace();

            throw new FileUploadException(file.getOriginalFilename());
        }
    }

    public String uploadImage(MultipartFile file, String extraPath) {
        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
            throw new FileUploadException(file.getOriginalFilename());
        }

        return this.uploadFile(file, extraPath);
    }

    private void validateDirectory(String fullPath) {
        File directory = new File(fullPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public String getUploadDir(String extraPath) {
        if (extraPath != null) {
            return this.uploadDir + extraPath;
        }

        return this.uploadDir;
    }
}
