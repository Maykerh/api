package com.zup.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zup.api.dto.request.AddressDTO;
import com.zup.api.dto.request.CustomerDTO;
import com.zup.api.dto.response.ProposalDataDTO;
import com.zup.api.entity.Address;
import com.zup.api.entity.Customer;
import com.zup.api.entity.Proposal;
import com.zup.api.enumerator.ProposalStatus;
import com.zup.api.error.exception.ProposalCustomerDocumentNotFoundException;
import com.zup.api.error.exception.ProposalNotFoundException;
import com.zup.api.repository.AddressRepository;
import com.zup.api.repository.CustomerRepository;
import com.zup.api.repository.AccountRepository;
import com.zup.api.repository.ProposalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private AccountService accountService;

    @Value("${file.upload.customer-documents}")
    private String customerDocumentsDir;

    /**
     * Primeiro passo - Criar a proposta e dados do cliente
     */
    public Proposal createNewProposal(CustomerDTO customerDTO) {
        Customer customer = this.customerRepository.save(customerDTO.toEntity());

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

        Address address = addressDTO.toEntity();
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

        String documentName = fileService.uploadImage(file, this.customerDocumentsDir);

        Customer customer = proposal.getCustomer();

        customer.setDocumentImage(documentName);

        this.customerRepository.save(customer);

        proposal.setStatus(ProposalStatus.CUSTOMER_DOCUMENT_SAVED);

        this.proposalRepository.save(proposal);
    }

    /**
     * Quarto passo (1) - Retornar os dados da proposta
     */
    public ProposalDataDTO getProposalData(String proposalId) {
        Proposal proposal = this.proposalRepository.findById(UUID.fromString(proposalId)).orElseThrow(() -> new ProposalNotFoundException());

        Customer customer = proposal.getCustomer();

        proposal.checkCustomerDocumentStep();

        this.checkDocumentExists(customer.getDocumentImage());

        ProposalDataDTO proposalDataDTO = new ProposalDataDTO();
        proposalDataDTO.setCustomer(CustomerDTO.fromEntity(customer));
        proposalDataDTO.setAddress(AddressDTO.fromEntity(customer.getAddress()));

        return proposalDataDTO;
    }

    /**
     * Quarto passo (2) - Aceitar a proposta
     */
    public Map<String, String> acceptProposal(String proposalId) {
        Proposal proposal = this.proposalRepository.findById(UUID.fromString(proposalId)).orElseThrow(() -> new ProposalNotFoundException());

        proposal.checkIfAllowAccept();

        Customer customer = proposal.getCustomer();

        this.checkDocumentExists(customer.getDocumentImage());

        proposal.setStatus(ProposalStatus.ACCEPTED);
        this.proposalRepository.save(proposal);

        ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(() -> {
            this.accountService.createNewAccount(proposal);
        });

        Map<String, String> response = new HashMap<String, String>();
        response.put("message", "Proposta aceita com sucesso. Em breve você receberá um email com as informações da sua nova conta.");

        return response;
    }

    /**
     * Quarto passo (2) - Recusar a proposta
     */
    public Map<String, String> declineProposal(String proposalId) {
        Proposal proposal = this.proposalRepository.findById(UUID.fromString(proposalId)).orElseThrow(() -> new ProposalNotFoundException());

        proposal.checkIfAllowDecline();
        
        Customer customer = proposal.getCustomer();

        this.checkDocumentExists(customer.getDocumentImage());

        proposal.setStatus(ProposalStatus.DECLINED);
        this.proposalRepository.save(proposal);

        Map<String, String> response = new HashMap<String, String>();

        response.put("message", "Que pena, seria uma honra ter você como cliente.");

        return response;
    }

    /**
     * Valida se o arquivo do documento do cliente existe
     */
    private void checkDocumentExists(String documentImageName) {
        if (!this.fileService.fileExists(documentImageName, this.customerDocumentsDir)) {
            throw new ProposalCustomerDocumentNotFoundException();
        }
    }
}
