package com.zup.api.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.zup.api.enumerator.ProposalStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", unique = true, updatable = false, nullable = false)
    private UUID id;

    private ProposalStatus status;

    @OneToOne
    private Customer client;

    public boolean validateClientDataIntegrity() {
        return true;
    }
}
