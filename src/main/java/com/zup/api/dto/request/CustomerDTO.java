package com.zup.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zup.api.entity.Customer;
import com.zup.api.utils.DTOMapper;
import com.zup.api.validation.MinimumAge;
import com.zup.api.validation.UniqueCPF;
import com.zup.api.validation.UniqueEmail;

import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
public class CustomerDTO {    
    @NotBlank(message = "Campo 'nome' é obrigatório")
    private String name;

    @NotBlank(message = "Campo 'sobrenome' é obrigatório")
    private String lastName;

    @NotBlank(message = "Campo 'email' é obrigatório")
    @Email(message = "Email com formato inválido")
    @UniqueEmail
    private String email;

    @NotNull(message = "Campo 'data de nascimento' é obrigatório")
    @MinimumAge
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @NotBlank(message = "Campo 'CPF' é obrigatório")
    @CPF(message = "CPF com formato inválido")
    @UniqueCPF
    private String cpf;

    public Customer toEntity() {
       return (Customer) DTOMapper.toEntity(this, Customer.class);
    }

    public static CustomerDTO fromEntity(Customer customer) {
        return (CustomerDTO) DTOMapper.fromEntity(customer, CustomerDTO.class);
	}
}

