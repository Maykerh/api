package com.zup.api.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

@Service
class ValidatingService {

	private Validator validator;

	ValidatingService(Validator validator) {
		this.validator = validator;
	}

	void validateCPF(String cpf) {
		Set<ConstraintViolation<String>> violations = validator.validate(cpf);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}
}