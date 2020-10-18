package com.zup.api.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.zup.api.error.exception.FileUploadException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Setter;

/**
 * Serviço responsável pelo upload de arquivos
 */
@Service
@Setter
@ConfigurationProperties(prefix = "file")
public class FileService {
    private String uploadDir;

    /**
     * Faz o upload de um arquivo qualquer
     * @param file arquivo recebido no request
     * @param extraPath path adicional
     * @return nome do arquivo
     */
    public String uploadFile(MultipartFile file, String extraPath) {
        try {
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = this.getFileNameHash(FilenameUtils.getBaseName(StringUtils.cleanPath(file.getOriginalFilename())));
            String path = FilenameUtils.separatorsToSystem(this.getUploadDir(extraPath));

            this.validateDirectory(path);

            Path fileFullPath = Paths.get(path + File.separator + fileName + '.' + fileExtension);

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

    public boolean fileExists(String fileName, String extraPath) {
        File file = new File(this.getAbsoluteUploadDir(extraPath) + File.separator + fileName);

        return file.exists();
    }

    public String getAbsoluteUploadDir(String extraPath) {
        Path fullPath = Paths.get(FilenameUtils.separatorsToSystem(this.getUploadDir(extraPath))).toAbsolutePath();

        return fullPath.toString();
    }

    public String getFileNameHash(String fileName) {
        String md5Hex = DigestUtils.md5Digest(fileName.getBytes()).toString();

        return md5Hex;
    }

    public String getUploadDir(String extraPath) {
        if (extraPath != null) {
            return this.uploadDir + File.separator + extraPath;
        }

        return this.uploadDir;
    }
}
