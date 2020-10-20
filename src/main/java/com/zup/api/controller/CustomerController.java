package com.zup.api.controller;

import java.util.Map;

import javax.validation.Valid;

import com.zup.api.dto.request.PasswordRequestDTO;
import com.zup.api.dto.request.PasswordCreationDTO;
import com.zup.api.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/password-request")
    public ResponseEntity<Object> requestPassword(@Valid @RequestBody PasswordRequestDTO passwordRequestDTO) {
        Map<String, String> response = this.customerService.requestPassword(passwordRequestDTO);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/password")
    public ResponseEntity<Object> createPassword(@Valid @RequestBody PasswordCreationDTO passwordCreationDTO) {
        Map<String, String> response = this.customerService.createPassword(passwordCreationDTO);

        return ResponseEntity.ok().body(response);
    }
}
