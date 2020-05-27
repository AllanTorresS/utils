package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.LeadCredenciamentoPostoIntegradorVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Contrato para implementação de repositório de integração dos dados de Lead no sistema externo.
 */
public interface ILeadCredenciamentoDados {
	
	/**
     * Criação de Lead com os dados informados.
     *
     * @param cnpj Parâmetro de identificação do Lead.
     * @param corpo Corpo da requisição com os atributos com os atributos para criação do Lead.
     * @return Identificador do Lead criado.
     * @throws ExcecaoValidacao Caso já exista um Lead para o CNPJ informado.
     */
    String criarLead(String cnpj, Object corpo) throws ExcecaoValidacao;

	/**
     * Atualização dos dados de Lead.
     *
     * @param cnpj Parâmetro de identificação do Lead.
     * @param corpo Corpo da requisição com os atributos que serão atualizados no Lead.
     * @throws ExcecaoValidacao Caso não exista um Lead para o CNPJ informado.
     */
    void atualizarLead(String cnpj, Object corpo) throws ExcecaoValidacao;
	
	/**
     * Armazenamento de anexo ao Lead.
     *
     * @param corpo Corpo da requisição.
     */
	void anexarLead(Object corpo);
	
	/**
     * Consulta as informações de Lead.
     *
     * @param cnpj Parâmetro de identificação do Lead.
     * @return {@link LeadCredenciamentoPostoIntegradorVo} Dados do lead de posto.
     * @throws ExcecaoValidacao Caso não exista dados de Lead para o posto com o CNPJ informado.
     */
	LeadCredenciamentoPostoIntegradorVo consultarPostoLead(String cnpj) throws ExcecaoValidacao;

	/**
     * Valida a existencia de Lead para o CNPJ informado.
     *
     * @param cnpj Parâmetro de busca do Lead.
     * @return true em caso de existencia do Lead, caso contrario false.
     */
	Boolean validarLeadExistente(String cnpj);
}