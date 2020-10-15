package com.zup.api.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import com.zup.api.dto.ClientDTO;
import com.zup.api.service.ProposalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/proposal")
public class ProposalController {
    @Autowired
    private ProposalService proposalService;

    @PostMapping("/create")
    public ResponseEntity<Object> newProposal(@Valid @RequestBody ClientDTO client) throws URISyntaxException {
        this.proposalService.createNewProposal(client);

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        URI uri = new URI(baseUrl + "/proposal/address");

        return ResponseEntity.created(uri).build();
    }
}
