package com.zup.api.error;

import com.zup.api.error.exception.ProposalClientDataNotFoundException;
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
	@ExceptionHandler(ProposalClientDataNotFoundException.class)
	ResponseEntity<Object> onProposalClientDataNotFoundException(ProposalClientDataNotFoundException e) {

        return ResponseEntity.unprocessableEntity().body(e.getMessage());
    }
}
