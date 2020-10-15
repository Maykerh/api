package com.zup.api.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate>{
    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        return birthDate.plusYears(18).isBefore(LocalDate.now());
    }
}
