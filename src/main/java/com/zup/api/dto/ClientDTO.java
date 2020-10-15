package com.zup.api.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zup.api.entity.Client;
import com.zup.api.validation.MinimumAge;
import com.zup.api.validation.UniqueCPF;
import com.zup.api.validation.UniqueEmail;

import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ClientDTO implements DTOInterface {    
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
    private LocalDate birthDate;

    @NotBlank(message = "Campo 'CPF' é obrigatório")
    @CPF(message = "CPF com formato inválido")
    @UniqueCPF
    private String cpf;

    public Client getEntity() {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(this, Client.class);
    }
}

