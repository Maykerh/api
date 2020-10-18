package com.zup.api.error.exception;

public class ProposalAlreadyAcceptedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProposalAlreadyAcceptedException() {
        super("Esta proposta ja foi aceita.");
    }
}
