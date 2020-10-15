package com.zup.api.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> error = new HashMap<String, String>();

		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return error;
  	}
}

// new Violation(fieldError.getField(), fieldError.getDefaultMessage()));