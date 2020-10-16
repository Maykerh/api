package com.zup.api.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import com.zup.api.dto.AddressDTO;
import com.zup.api.dto.CustomerDTO;
import com.zup.api.entity.Proposal;
import com.zup.api.error.exception.ProposalNotFoundException;
import com.zup.api.entity.Proposal;
import com.zup.api.service.ProposalService;
import com.zup.api.utils.URIBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proposal")
public class ProposalController {
    @Autowired
    private ProposalService proposalService;

    @PostMapping("/create")
    public ResponseEntity<Object> newProposal(@Valid @RequestBody CustomerDTO clientDTO) throws URISyntaxException {
        Proposal proposal = this.proposalService.createNewProposal(clientDTO);

        URI locationURI = URIBuilder.getLocationURI("proposal/{id}/address/", proposal.getId().toString());

        return ResponseEntity.created(locationURI).build();
    }

    @PostMapping("/{id}/address")
    public ResponseEntity<Object> newProposal(@PathVariable(value = "id") String proposalId, @Valid @RequestBody AddressDTO addressDTO) throws URISyntaxException {
        this.proposalService.addAddress(proposalId, addressDTO);

        URI locationURI = URIBuilder.getLocationURI("proposal/{id}/document-image/", proposalId.toString());

        return ResponseEntity.created(locationURI).build();
    }
}
