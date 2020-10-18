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
import com.zup.api.error.exception.ProposalAlreadyAcceptedException;
import com.zup.api.error.exception.ProposalCustomerAddressNotFoundException;
import com.zup.api.error.exception.ProposalCustomerAlreadyHasDocumentException;
import com.zup.api.error.exception.ProposalCustomerDataNotFoundException;
import com.zup.api.error.exception.ProposalCustomerDocumentNotFoundException;

@Entity
@Getter @Setter
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, updatable = false, nullable = false)
    private UUID id;

    private ProposalStatus status;

    @OneToOne
    private Customer customer;

    public void checkCustomerDataStep() {
        if (this.getStatus() == null || this.getStatus().ordinal() < ProposalStatus.CUSTOMER_DATA_SAVED.ordinal() || this.getCustomer() == null) {
            throw new ProposalCustomerDataNotFoundException();
        }
    }

    public void checkCustomerAddressStep() {
        this.checkCustomerDataStep();

        Address address = this.getCustomer().getAddress();

        if (this.getStatus() == null || this.getStatus().ordinal() < ProposalStatus.CUSTOMER_ADDRESS_SAVED.ordinal() || address == null) {
            throw new ProposalCustomerAddressNotFoundException();
        }
    }

    public void checkCustomerAlreadyHasDocument() {
        if (this.getCustomer().getDocumentImage() != null) {
            throw new ProposalCustomerAlreadyHasDocumentException();
        }
    }

    public void checkCustomerDocumentStep() {
        this.checkCustomerAddressStep();

        if (this.getStatus() == null || this.getStatus().ordinal() < ProposalStatus.CUSTOMER_DOCUMENT_SAVED.ordinal() || this.getCustomer().getDocumentImage() == null) {
            throw new ProposalCustomerDocumentNotFoundException();
        }
    }

	public void checkIfAllowAccept() {
        this.checkCustomerDocumentStep();

        if (ProposalStatus.ACCEPTED.equals(this.getStatus())) {
            throw new ProposalAlreadyAcceptedException();
        }
    }
    
    public void checkIfAllowDecline() {
        this.checkCustomerDocumentStep();
        
        if (ProposalStatus.ACCEPTED.equals(this.getStatus())) {
            throw new ProposalAlreadyAcceptedException();
        }
	}
}
