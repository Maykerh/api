package com.zup.api.service;

import java.util.Optional;
import java.util.UUID;

import com.zup.api.dto.AddressDTO;
import com.zup.api.dto.CustomerDTO;
import com.zup.api.entity.Address;
import com.zup.api.entity.Customer;
import com.zup.api.entity.Proposal;
import com.zup.api.repository.AddressRepository;
import com.zup.api.repository.CustomerRepository;
import com.zup.api.repository.ProposalRepository;
import com.zup.api.enumerator.ProposalStatus;
import com.zup.api.error.exception.ProposalClientDataNotFoundException;
import com.zup.api.error.exception.ProposalNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Primeiro passo - Criar a proposta e dados do cliente
     */
    public Proposal createNewProposal(CustomerDTO customerDTO) {
        Customer customer = this.customerRepository.save(customerDTO.getEntity());

        Proposal proposal = new Proposal();

        proposal.setStatus(ProposalStatus.CLIENT_DATA_SAVED);
        proposal.setClient(customer);

        return this.proposalRepository.save(proposal);
    }

    /**
     * Segundo passo - Salvar o endereço do cliente
     * 
     * @throws ProposalNotFoundException
     * @throws ProposalClientDataNotFoundException
     */
    public void addAddress(String proposalId, AddressDTO addressDTO) {
        Proposal proposal = proposalRepository.findById(UUID.fromString(proposalId)).orElseThrow(() -> new ProposalNotFoundException());
        
        proposal.checkClientData();

        Address address = addressDTO.getEntity();

        address.setClient(proposal.getClient());

        this.addressRepository.save(addressDTO.getEntity());
    }
}
