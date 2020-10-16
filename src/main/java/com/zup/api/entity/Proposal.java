package com.zup.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.zup.api.enumerator.ProposalStatus;
import com.zup.api.error.exception.ProposalClientDataNotFoundException;

@Entity
@Getter @Setter
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    private UUID id;

    private ProposalStatus status;

    @OneToOne
    private Customer client;

    public void checkClientData() throws ProposalClientDataNotFoundException {
        if (!ProposalStatus.CLIENT_DATA_SAVED.equals(this.getStatus()) || this.getClient() == null) {
            throw new ProposalClientDataNotFoundException();
        }
    }
}
