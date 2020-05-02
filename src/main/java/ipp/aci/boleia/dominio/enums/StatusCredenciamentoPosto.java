package ipp.aci.boleia.dominio.enums;

/**
 * Indica status de credenciamento do posto.
 */
public enum StatusCredenciamentoPosto {

	/**Caso o posto esteja habilitado*/
	HABILITADO,

	/**Caso o posto esteja aguardando habilitação(pendente de aceite)*/
	PENDENTE_ACEITE,
	
	/**Caso um usuário tente credenciar o posto do cnpj informado que já se encontra em credenciamento*/
	OUTRO_USUARIO,
	
	/**Caso o usuário credencie um novo posto de outra rede de seus habilitados*/
	OUTRA_REDE,
	
	/**Caso o mesmo usuário possua um posto em credenciamento e tente credenciar outro posto*/
	CREDENCIAMENTO_PENDENTE,
	
	/**Iniciar o credenciamento de um novo posto e usuário*/
	INICIAR_CREDENCIAMENTO,

    /**
     * Caso a habilitação esteja apta para ser iniciada
     */
    INICIAR_HABILITACAO,

    /**
     * Caso o ponto de venda possua for bandeira branca(inexistente)
     */
    BANDEIRA_BRANCA,
	
	/**Caso o token do credenciamento do ponto de venda esteja válido*/
	INICIADO_CREDENCIAMENTO_VALIDO,
	
	/**Caso o token do credenciamento do ponto de venda esteja expirado*/
    INICIADO_CREDENCIAMENTO_EXPIRADO,

    /**Caso o credenciamento para o CNPJ informado tenha iniciado através da plataforma do Salesforce */
	INICIADO_SALESFORCE,
}
