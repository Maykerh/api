package com.zup.api.dto.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordRequestDTO {
    @NotBlank(message = "Campo 'CPF' é obrigatório")
    @CPF(message = "CPF com formato inválido")
    private String cpf;
}
