package com.zup.api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordCreationDTO {
    @NotBlank(message = "Campo 'CPF' é obrigatório")
    @CPF(message = "CPF com formato inválido")
    private String cpf;

    @NotNull(message = "Campo 'Código' é obrigatório")
    private Integer validationToken;

    @NotBlank(message = "Campo 'Senha' é obrigatório")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[!@#$&*._\\-\\+])(?=.*[0-9])(?=.*[a-z]).{8,32}$",
        message = "A senha deve conter de 8 a 32 caracteres, uma letra maiúscula, uma minúscula, um número e um caracter especial"
    )
    private String password;
}
