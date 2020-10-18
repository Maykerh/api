package com.zup.api.dto.response;

import com.zup.api.dto.request.CustomerDTO;
import com.zup.api.dto.request.AddressDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProposalDataDTO {
    CustomerDTO customer;
    AddressDTO address;
}
