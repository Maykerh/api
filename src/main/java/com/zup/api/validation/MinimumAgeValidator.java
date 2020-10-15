package com.zup.api.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class MinimumAgeValidator implements ConstraintValidator<MinimumAge, String>{
    @Override
    public boolean isValid(String birthDate, ConstraintValidatorContext context) {
        LocalDate date;

        try {
            date = LocalDate.parse(birthDate);
        } catch (Exception e) {
            return false;
        }

        return date.plusYears(18).isBefore(LocalDate.now());
    }
}
