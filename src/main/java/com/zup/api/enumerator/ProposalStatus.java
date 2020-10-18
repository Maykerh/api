package com.zup.api.enumerator;

/**
 * Status do Andamento da proposta
 */
public enum ProposalStatus {
    /**
     * 0 - Proposta criada e dados basicos do cliente salvos com sucesso
     */
    CUSTOMER_DATA_SAVED,

    /**
     * 1 - Endereço do cliente cadastrado com sucesso
     */
    CUSTOMER_ADDRESS_SAVED,

    /**
     * 2 - Documento do cliente cadastrado com sucesso
     */
    CUSTOMER_DOCUMENT_SAVED,

    /**
     * 3 - Proposta aceita pelo cliente
     */
    ACCEPTED,

    /**
     * 4 - Proposta recusada pelo cliente
     */
    DECLINED;
}
