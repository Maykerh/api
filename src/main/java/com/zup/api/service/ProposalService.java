package com.zup.api.service;

import com.zup.api.dto.ClientDTO;
import com.zup.api.entity.Client;
import com.zup.api.entity.Proposal;
import com.zup.api.repository.ClientRepository;
import com.zup.api.repository.ProposalRepository;
import com.zup.api.enumerator.ProposalStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ClientRepository clientRepository;

    public void createNewProposal(ClientDTO clientData) {
        Client client = this.clientRepository.save(clientData.getEntity());

        Proposal proposal = new Proposal();

        proposal.setStatus(ProposalStatus.CLIENT_DATA);
        proposal.setClient(client);
        
        this.proposalRepository.save(proposal).toString();
    }
}
