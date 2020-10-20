package com.zup.api.error.exception;

public class CustomerDataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomerDataNotFoundException() {
        super("Dados do cliente não encontrados");
    }
}
