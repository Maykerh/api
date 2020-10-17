package com.zup.api.service;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import com.zup.api.dto.AddressDTO;
import com.zup.api.dto.CustomerDTO;
import com.zup.api.entity.Address;
import com.zup.api.entity.Customer;
import com.zup.api.entity.Proposal;
import com.zup.api.repository.AddressRepository;
import com.zup.api.repository.CustomerRepository;
import com.zup.api.repository.ProposalRepository;
import com.zup.api.enumerator.ProposalStatus;
import com.zup.api.error.exception.ProposalCustomerDataNotFoundException;
import com.zup.api.error.exception.ProposalNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Serviço responsavel pelas etapas da solicitação de proposta de abertura de conta
 */
@Service
public class ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private FileService fileService;

    /**
     * Primeiro passo - Criar a proposta e dados do cliente
     */
    public Proposal createNewProposal(CustomerDTO customerDTO) {
        Customer customer = this.customerRepository.save(customerDTO.getEntity());

        Proposal proposal = new Proposal();

        proposal.setStatus(ProposalStatus.CUSTOMER_DATA_SAVED);
        proposal.setCustomer(customer);

        return this.proposalRepository.save(proposal);
    }

    /**
     * Segundo passo - Salvar o endereço do cliente
     */
    public void addAddress(String proposalId, AddressDTO addressDTO) {
        Proposal proposal = proposalRepository.findById(UUID.fromString(proposalId)).orElseThrow(() -> new ProposalNotFoundException());
        
        proposal.checkCustomerDataStep();

        Address address = addressDTO.getEntity();
        address.setCustomer(proposal.getCustomer());

        this.addressRepository.save(address);

        proposal.setStatus(ProposalStatus.CUSTOMER_ADDRESS_SAVED);
        this.proposalRepository.save(proposal);
    }

    /**
     * Terceiro passo - Salvar imagem do documento do cliente
     */
    public void addDocument(String proposalId, MultipartFile file) {
        Proposal proposal = this.proposalRepository.findById(UUID.fromString(proposalId)).orElseThrow(() -> new ProposalNotFoundException());

        proposal.checkCustomerAddressStep();
        proposal.checkCustomerAlreadyHasDocument();

        String documentName = fileService.uploadImage(file, "/images/documents");

        Customer customer = proposal.getCustomer();

        customer.setDocumentImage(documentName);

        this.customerRepository.save(customer);

        proposal.setStatus(ProposalStatus.CUSTOMER_DOCUMENT_SAVED);

        this.proposalRepository.save(proposal);
    }
}
