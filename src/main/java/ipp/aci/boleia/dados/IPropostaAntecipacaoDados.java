package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PropostaAntecipacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAntecipacaoVo;

/**
 * Contrato de implementação de repositório de Proposta Amtecipacao
 */
public interface IPropostaAntecipacaoDados extends IRepositorioBoleiaDados<PropostaAntecipacao> {

    /**
     * Obtém uma proposta de antecipação dado um identificador da proposta
     *
     * @param idProposta id da proposta de antecipação
     * @return a proposta de antecipação localizada
     */
    PropostaAntecipacao obterPorIdParceiro(String idProposta);

    /**
     * Pesquisa antecipação paginado a partir do filtro informado
     *
     * @param filtro o filtro da busca
     * @return uma lista de resultado paginado localizadas
     */
    ResultadoPaginado<PropostaAntecipacao> pesquisarAntecipacoesReembolso(FiltroPesquisaAntecipacaoVo filtro);
}
