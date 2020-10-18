package com.zup.api.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.validation.Valid;

import com.zup.api.dto.request.AddressDTO;
import com.zup.api.dto.request.CustomerDTO;
import com.zup.api.dto.response.ProposalDataDTO;
import com.zup.api.entity.Proposal;
import com.zup.api.service.ProposalService;
import com.zup.api.utils.URIBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/proposal")
public class ProposalController {
    @Autowired
    private ProposalService proposalService;

    @PostMapping("/create")
    public ResponseEntity<Object> newProposal(@Valid @RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        Proposal proposal = this.proposalService.createNewProposal(customerDTO);

        URI locationURI = URIBuilder.getLocationURI("proposal/{id}/address", proposal.getId().toString());

        return ResponseEntity.created(locationURI).build();
    }

    @PostMapping("/{id}/address")
    public ResponseEntity<Object> addAddress(@PathVariable(value = "id") String proposalId, @Valid @RequestBody AddressDTO addressDTO) throws URISyntaxException {
        this.proposalService.addAddress(proposalId, addressDTO);

        URI locationURI = URIBuilder.getLocationURI("proposal/{id}/document", proposalId.toString());

        return ResponseEntity.created(locationURI).build();
    }

    @PostMapping("/{id}/document")
    public ResponseEntity<Object> addDocument(@PathVariable(value = "id") String proposalId, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        this.proposalService.addDocument(proposalId, file);

        URI locationURI = URIBuilder.getLocationURI("proposal/{id}", proposalId.toString());

        return ResponseEntity.created(locationURI).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProposalDataDTO> getProposalData(@PathVariable(value = "id") String proposalId) throws URISyntaxException {
        ProposalDataDTO dto = this.proposalService.getProposalData(proposalId);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value="/{id}/accept")
    public ResponseEntity<Object> acceptProposal(@PathVariable(value = "id") String proposalId) {
        Map<String, String> response = this.proposalService.acceptProposal(proposalId);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value="/{id}/decline")
    public ResponseEntity<Object> declineProposal(@PathVariable(value = "id") String proposalId) {
        Map<String, String> response = this.proposalService.declineProposal(proposalId);

        return ResponseEntity.ok().body(response);
    }
}
