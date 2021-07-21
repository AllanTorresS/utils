package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PropostaAntecipacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAntecipacaoVo;

/**
 * Contrato de implementação de repositório de Proposta Amtecipacao
 */
public interface IPropostaAntecipacaoDados extends IRepositorioBoleiaDados<PropostaAntecipacao> {

    PropostaAntecipacao obterPorIdParceiro(String idProposta);

    ResultadoPaginado<PropostaAntecipacao> pesquisarAntecipacoesReembolso(FiltroPesquisaAntecipacaoVo filtro);
}
