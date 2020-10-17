package com.zup.api.error.exception;

public class ProposalCustomerAlreadyHasDocumentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProposalCustomerAlreadyHasDocumentException() {
        super("Cliente já possui um documento cadastrado");
    }
}
