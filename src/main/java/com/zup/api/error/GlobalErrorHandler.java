package com.zup.api.error;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.zup.api.error.exception.FileUploadException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
class GlobalErrorHandler {

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> error = new HashMap<String, String>();

		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return error;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	Map<String, String> onInvalidFormatException(InvalidFormatException e) {
		Map<String, String> error = new HashMap<String, String>();

		String fieldName = "";

		for (Reference ref : e.getPath()) {
			if (ref.getFieldName() != null) {
				fieldName = ref.getFieldName();
			}
		};

		error.put(fieldName, "Valor '" + e.getValue() + "' inválido");
		
		return error;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(FileUploadException.class)
	Map<String, String> onFileUploadException(FileUploadException e) {
		Map<String, String> error = new HashMap<String, String>();

		error.put("message", e.getMessage());

		return error;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestPartException.class)
	public Map<String, String> handleMissingParams(MissingServletRequestPartException e) {
		String name = e.getRequestPartName();

		Map<String, String> error = new HashMap<String, String>();

		error.put("message", "Parametro '" + name + "' não informado");

		return error;
	}
}