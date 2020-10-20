package com.zup.api.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zup.api.entity.Customer;
import com.zup.api.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, String> {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        cpf = cpf.replace(".", "").replace("-", "");

        Optional<Customer> client = this.customerRepository.findByCpf(cpf);

        return client.isEmpty();
    }
}
