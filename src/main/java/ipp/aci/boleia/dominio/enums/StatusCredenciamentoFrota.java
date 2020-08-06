package ipp.aci.boleia.dominio.enums;

/**
 * Indica status de credenciamento da frota.
 */
public enum StatusCredenciamentoFrota {

	/**Iniciar o credenciamento da frota*/
	INICIAR,
	
	/**Frota já habilitada*/
	HABILITADO,
	
	/**Aguardando habilitação da frota*/
	AGUARDANDO_HABILITACAO,
	
	/**Caso o CNPJ esteja em credenciamento*/
	CREDENCIAMENTO_PENDENTE,
	
	/**Continua o credenciamento com token válido*/
	CONTINUAR_VALIDO,
	
	/**Token do credenciamento expirado*/
	CONTINUAR_EXPIRADO,
	
	/**Credenciamento finlizado para o CNPJ informado*/
	FINALIZADO,
	
	/**Caso o credenciamento para o CNPJ informado tenha iniciado através da plataforma do Salesforce */
	INICIADO_SALESFORCE;
	
}