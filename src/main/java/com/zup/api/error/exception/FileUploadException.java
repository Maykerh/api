package com.zup.api.error.exception;

public class FileUploadException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileUploadException(String fileName) {
        super("Não foi possível fazer upload do arquivo " + fileName);
    }    
}
