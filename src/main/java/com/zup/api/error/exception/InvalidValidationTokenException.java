package com.zup.api.error.exception;

public class InvalidValidationTokenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidValidationTokenException() {
        super("O código informado é inválido.");
    }
}
