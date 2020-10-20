package com.zup.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String>{
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return true;
    }
}

