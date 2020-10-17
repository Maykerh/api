package com.zup.api.error.exception;

public class ProposalCustomerDataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProposalCustomerDataNotFoundException() {
        super("Dados do cliente não encontrados");
    }
}
