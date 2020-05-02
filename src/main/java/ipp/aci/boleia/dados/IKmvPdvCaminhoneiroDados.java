package ipp.aci.boleia.dados;

import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Contrato para implementacao de repositorios
 * do KMV PDV Caminhoneiro
 */
public interface IKmvPdvCaminhoneiroDados {

    /**
     * Busca bicos de um ponto de venda
     *
     * @param codigoCorporativoPontoDeVenda codigo corporativo do Ponto de Venda
     * @return Json recebido com os bicos
     * @throws ExcecaoValidacao em caso de falha de comunicacao ao buscar os dados
     */
    String buscarBicos(Long codigoCorporativoPontoDeVenda) throws ExcecaoValidacao;

    /**
     * Busca todos os abastecimentos das duas ultimas horas de um bico do ponto de venda
     *
     * @param codigoCorporativoPontoDeVenda codigo corporativo do Ponto de Venda
     * @param idBico o id do bico escolhido
     * @return Json recebido com os abastecimentos
     * @throws ExcecaoValidacao em caso de falha de comunicacao ao buscar os dados
     */
    String buscarAbastecimentos(Long codigoCorporativoPontoDeVenda, String idBico) throws ExcecaoValidacao;

    /**
     * Busca parâmetros do KMV PDV Caminhoneiro de um ponto de venda
     *
     * @param codigoCorporativoPontoDeVenda codigo corporativo do Ponto de Venda
     * @return Json recebido com os parâmetros
     * @throws ExcecaoValidacao em caso de falha de comunicacao ao buscar os dados
     */
    String buscarParametros(Long codigoCorporativoPontoDeVenda) throws ExcecaoValidacao;
}
