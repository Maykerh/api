package com.zup.api.error.exception;

public class ProposalCustomerDocumentNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProposalCustomerDocumentNotFoundException() {
        super("O documento do cliente vinculado a proposta não foi encontrado");
    }    
}
