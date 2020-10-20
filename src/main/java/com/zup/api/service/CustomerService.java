package com.zup.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zup.api.dto.request.PasswordCreationDTO;
import com.zup.api.dto.request.PasswordRequestDTO;
import com.zup.api.email.EmailMessage;
import com.zup.api.email.EmailService;
import com.zup.api.entity.Customer;
import com.zup.api.error.exception.CustomerDataNotFoundException;
import com.zup.api.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelas ações do cliente
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService;
    
    public Map<String, String> requestPassword(PasswordRequestDTO passwordRequestDTO) {
        String cpf = passwordRequestDTO.getCpf().replace(".", "").replace("-", "");
        Customer customer = this.customerRepository.findByCpf(cpf).orElseThrow(() -> new CustomerDataNotFoundException());

        Integer validationToken = customer.newValidationToken();

        this.customerRepository.save(customer);

        EmailMessage email = new EmailMessage();
        
        email.setTo(customer.getEmail());
        email.setSubject("Nosso Banco - Criação de senha");

        StringBuilder body = new StringBuilder();

        body.append("Olá " + customer.getName());
        body.append("\n\nUtilize este código para criar sua nova senha: " + validationToken);

        email.setBody(body.toString());

        ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(() -> {
            this.emailService.send(email);
        });

        Map<String, String> response = new HashMap<String, String>();
        response.put("message", "Confira no seu email o código para a criação da nova senha");

        return response;
    }
    
    public Map<String, String> createPassword(PasswordCreationDTO passwordCreationDTO) {
        Customer customer = this.customerRepository.findByCpf(passwordCreationDTO.getCpf()).orElseThrow(() -> new CustomerDataNotFoundException());

        customer.validateValidationToken(passwordCreationDTO.getValidationToken());

        customer.setValidationToken(null);
        customer.setValidationTokenExpireDate(null);


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        customer.setPassword(passwordEncoder.encode(passwordCreationDTO.getPassword()));

        this.customerRepository.save(customer);

        EmailMessage email = new EmailMessage();
        
        email.setTo(customer.getEmail());
        email.setSubject("Nosso Banco - Nova senha cadastrada");

        StringBuilder body = new StringBuilder();

        body.append("Olá " + customer.getName());
        body.append("\n\nSua nova senha foi cadastrada com sucesso!");

        email.setBody(body.toString());

        ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(() -> {
            this.emailService.send(email);
        });

        Map<String, String> response = new HashMap<String, String>();
        response.put("message", "Senha cadastrada com sucesso");

        return response;
    }
}
