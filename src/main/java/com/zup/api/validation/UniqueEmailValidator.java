package com.zup.api.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zup.api.entity.Client;
import com.zup.api.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Optional<Client> client = this.clientRepository.findByEmail(email);

        return client.isEmpty();
    }
}
