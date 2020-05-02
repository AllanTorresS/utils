package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.TransacaoConsolidadaDetalhe;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaDetalheVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades transação consolidada detalhe
 */
public interface ITransacaoConsolidadaDetalheDados extends IRepositorioBoleiaDados<TransacaoConsolidadaDetalhe> {

    /**
     * Busca detalhes de transacao de acordo com filtro da tela
     * @param filtro preenchido na tela
     * @return resultado paginado para preenchimento do grid na tela
     */
    ResultadoPaginado<TransacaoConsolidadaDetalhe> pesquisar(FiltroPesquisaTransacaoConsolidadaDetalheVo filtro);

    /**
     * Busca os detalhes da transacao para um dado produto o ultimo ciclo fechado em relacao a data informada
     * @param idFrota A frota
     * @param idPv O pv
     * @param idTipoCombustivel O produto
     * @param data A data desejada
     * @return Lista de detalhes
     */
    List<TransacaoConsolidadaDetalhe> obterDetalhesTransacaoPorFrotaPvCombustivel(Long idFrota, Long idPv, Long idTipoCombustivel, Date data);
}
