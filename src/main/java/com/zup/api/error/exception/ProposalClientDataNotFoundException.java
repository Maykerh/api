package com.zup.api.error.exception;

public class ProposalClientDataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProposalClientDataNotFoundException() {
        super("Dados do cliente não encontrados");
    }
}
