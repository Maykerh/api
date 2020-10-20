package com.zup.api.error;

import com.zup.api.error.exception.ProposalAlreadyAcceptedException;
import com.zup.api.error.exception.ProposalCustomerAddressNotFoundException;
import com.zup.api.error.exception.ProposalCustomerAlreadyHasDocumentException;
import com.zup.api.error.exception.ProposalCustomerDocumentNotFoundException;
import com.zup.api.error.exception.ProposalNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
class ProposalErrorHandler {
    @ResponseBody
	@ExceptionHandler(ProposalNotFoundException.class)
	ResponseEntity<Object> onProposalNotFoundException(ProposalNotFoundException e) {
        return ResponseEntity.notFound().build();
	}

    @ResponseBody
	@ExceptionHandler(ProposalCustomerAddressNotFoundException.class)
	ResponseEntity<Object> onProposalCustomerAddressNotFoundException(ProposalCustomerAddressNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ResponseBody
	@ExceptionHandler(ProposalCustomerAlreadyHasDocumentException.class)
	ResponseEntity<Object> onProposalCustomerAlreadyHasDocumentException(ProposalCustomerAlreadyHasDocumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ResponseBody
	@ExceptionHandler(ProposalCustomerDocumentNotFoundException.class)
	ResponseEntity<Object> onProposalCustomerDocumentNotFoundException(ProposalCustomerDocumentNotFoundException e) {
        return ResponseEntity.unprocessableEntity().body(e.getMessage());
    }

    @ResponseBody
	@ExceptionHandler(ProposalAlreadyAcceptedException.class)
	ResponseEntity<Object> onProposalAlreadyAcceptedException(ProposalAlreadyAcceptedException e) {
       return ResponseEntity.badRequest().body(e.getMessage());
    }
}
