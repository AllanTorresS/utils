package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Reembolso
 */
public interface IReembolsoDados extends IRepositorioBoleiaDados<Reembolso> {

    /**
     * Pesquisa Reembolsos a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Reembolso> pesquisar(FiltroPesquisaReembolsoVo filtro);

    /**
     * Pesquisa Reembolsos para Exportação a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    List<Reembolso> pesquisarParaExportacao(FiltroPesquisaReembolsoVo filtro);

    /**
     * Pesquisa os reembolsos candidatos a realizar consulta no JDE
     * para consultar o status do aviso de crédito
     *
     * @return Os reembolsos candidatos a consulta de aviso de crédito.
     */
    List<Reembolso> buscarReembolsosParaConsultarAvisoCredito();

    /**
     * Obtem o último reembolso gerado para determinado ponto de venda
     * @param idPtov id do ponto de venda
     * @return Reembolso encontrado
     */
    Reembolso obterUltimoReembolso(Long idPtov);

    /**
     * Obtém uma lista de reembolsos com status Suspenso para Pagamento.
     *
     * @return Lista de reembolsos elegíveis para liberação de pagamento.
     */
    List<Reembolso> obterReembolsosSuspensosParaPagamento();

    /**
     * Retorna os reembolsos com erro de integração.
     *
     * @param numeroTentativas Número máximo de tentativas considerado na consulta
     * @return lista com os reembolsos
     */
    List<Reembolso> obterReembolsosErroEnvio(Integer numeroTentativas);
}
