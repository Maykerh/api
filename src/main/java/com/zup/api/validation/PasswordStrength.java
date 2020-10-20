package com.zup.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Documented
public @interface PasswordStrength {
    String message() default "A senha deve conter no mínimo 8 caracteres, uma letra maiúscula, uma minúscula e um caracter especial";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
