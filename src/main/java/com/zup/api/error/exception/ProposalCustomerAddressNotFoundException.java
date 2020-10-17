package com.zup.api.error.exception;

public class ProposalCustomerAddressNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProposalCustomerAddressNotFoundException() {
        super("Endereço do cliente não encontrado");
    }
}
