package com.zup.api.entity;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter @Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private Integer agency;

    @Column(unique = true, nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Integer bankCode;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "account")
    private Proposal proposal;
}
