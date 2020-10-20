package com.zup.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import com.zup.api.error.exception.InvalidValidationTokenException;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    private String cpf;
    
    @Column(unique = true)
    private String documentImage;

    private String password;

    private Integer validationToken;

    private LocalDateTime validationTokenExpireDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private Address account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private Proposal proposal;

    @PrePersist
    private void normalizeCPF() {
        String cpf = this.getCpf();

        cpf = cpf.replace(".", "").replace("-", "");

        this.setCpf(cpf);
    }

    public Integer newValidationToken() {
        Integer secondsToExpire = 300;  
        Integer token = 100000 + new Random().nextInt(999999);
        LocalDateTime expireDate = LocalDateTime.now().plusSeconds(secondsToExpire);

        this.setValidationToken(token);
        this.setValidationTokenExpireDate(expireDate);

        return token;
    }

    public void validateValidationToken(Integer incomingToken) {
        Integer token = this.getValidationToken();

        if (token == null || !token.equals(incomingToken)) {
            throw new InvalidValidationTokenException();
        }

        LocalDateTime expireDate = this.getValidationTokenExpireDate();

        if (expireDate == null || expireDate.isBefore(LocalDateTime.now())) {
            throw new InvalidValidationTokenException();
        }
    }
}
