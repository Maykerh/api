package com.zup.api.service;

import com.zup.api.dto.CustomerDTO;
import com.zup.api.entity.Customer;
import com.zup.api.entity.Proposal;
import com.zup.api.repository.CustomerRepository;
import com.zup.api.repository.ProposalRepository;
import com.zup.api.enumerator.ProposalStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void createNewProposal(CustomerDTO clientDTO) {
        Customer client = this.customerRepository.save(clientDTO.getEntity());

        Proposal proposal = new Proposal();

        proposal.setStatus(ProposalStatus.CLIENT_DATA);
        proposal.setClient(client);
        
        this.proposalRepository.save(proposal).toString();
    }

    public void addAddress(String proposalId, AddressDTO addressDTO) {
        Proposal proposal = proposalRepository.findOne(proposalId);

        // vericiar se criar um dto manualmente não vai dar problema no autowired das validações
        // verificar somente o proposal status se está no passo certo e se acha os dados do cliente pelo id da proposta
        if (!proposal.validateClientDataIntegrity()) {

        }

        Address address = this.addressRepository.save(addressDTO.getEntity());


    }
}
