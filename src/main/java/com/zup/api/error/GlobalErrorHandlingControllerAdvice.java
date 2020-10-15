package com.zup.api.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalErrorHandlingControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	List<String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<String> error = new ArrayList<String>();

		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.add(fieldError.getDefaultMessage());
		}

		return error;
  	}
}

// new Violation(fieldError.getField(), fieldError.getDefaultMessage()));