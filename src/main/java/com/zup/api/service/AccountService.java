package com.zup.api.service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zup.api.entity.Account;
import com.zup.api.entity.Customer;
import com.zup.api.entity.Proposal;
import com.zup.api.email.EmailMessage;
import com.zup.api.email.EmailService;
import com.zup.api.enumerator.ProposalStatus;
import com.zup.api.repository.AccountRepository;
import com.zup.api.repository.ProposalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelas ações de cadastro de conta
 */
@Service
public class AccountService {
    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailService emailService;
    
    public void createNewAccount(Proposal proposal) { 
        if (!this.validateCustomerData(proposal)) {
            return;
        }

        Account account = new Account();
        Random random = new Random();

        account.setAgency(1000 + random.nextInt(9999));
        account.setNumber(10000000 + random.nextInt(99999999));
        account.setBankCode(123);
        account.setBalance(new BigDecimal(0));
        account.setCustomer(proposal.getCustomer());

        this.accountRepository.save(account);

        proposal.setStatus(ProposalStatus.ACCOUNT_CREATED);
        proposal.setAccount(account);

        this.proposalRepository.save(proposal);
        
        this.sendAccountCreatedEmail(proposal.getCustomer(), account);
    }

    private void sendAccountCreatedEmail(Customer customer, Account account) {
        EmailMessage email = new EmailMessage();
        
        email.setTo(customer.getEmail());
        email.setSubject("Nosso Banco - Dados da sua nova conta");

        StringBuilder body = new StringBuilder();

        body.append("Olá " + customer.getName());
        body.append("\n\nEstes são os dados da sua conta:");
        body.append("\nAgência: " + account.getAgency());
        body.append("\nConta: " + account.getNumber());

        email.setBody(body.toString());

        this.emailService.send(email);
    }

    private boolean validateCustomerData(Proposal proposal) {
        Boolean validation = false;

        try {
            validation = this.fakeExternalServiceCall(1);
        } catch(Exception e) {
            return false;
        }

        if (!validation) {
            proposal.setStatus(ProposalStatus.EXTERNAL_VALIDATION_REFUSED);
            this.proposalRepository.save(proposal);

            return false;
        }

        return true;
    }

    private boolean fakeExternalServiceCall(Integer tryCounter) throws Exception {
        Integer tryLimit = 2;
        Boolean externalValidation = false;

        try {
            // Chamada externa
            externalValidation = true;
        } catch(Exception e) {
            tryCounter++;

            if (tryCounter > tryLimit) {
                throw new Exception("Serviço de validação indisponível");
            }

            externalValidation = this.fakeExternalServiceCall(tryCounter);
        }

        return externalValidation;
    }
}
