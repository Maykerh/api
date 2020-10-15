package com.zup.api.enumerator;

/**
 * Andamento da proposta
 * 0 - Proposta criada com os dados do cliente (entity.Client)
 * 1 - Endereco do cliente ja cadastrado (entity.Address)
 */
public enum ProposalStatus {
    CLIENT_DATA, CLIENT_ADDRRESS;
}
