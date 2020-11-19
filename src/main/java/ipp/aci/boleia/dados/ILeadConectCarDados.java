package ipp.aci.boleia.dados;

/**
 * Contrato para implementação de repositório de integração dos dados de Lead no sistema externo.
 */
public interface ILeadConectCarDados {
	
	/**
     * Envia Lead com os dados informados.
     *
     * @param corpo Corpo da requisição com os atributos com os atributos para criação do Lead.
     * @return se o Lead foi criado.
     */
    boolean enviarLead(Object corpo);

	
}