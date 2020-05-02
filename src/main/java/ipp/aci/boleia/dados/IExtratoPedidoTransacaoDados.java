package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ExtratoPedidoTransacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaExtratoPedidoCreditoVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades de um ExtratoPedidoTransacao
 */
public interface IExtratoPedidoTransacaoDados extends IRepositorioBoleiaDados<ExtratoPedidoTransacao> {

    /**
     * Pesquisa extratos de transacao para exibicao paginada em tela
     * @param filtro Os filtros informados
     * @return A lista de resultados
     */
    ResultadoPaginado<ExtratoPedidoTransacao> pesquisar(FiltroPesquisaExtratoPedidoCreditoVo filtro);

    /**
     * Pesquisa extratos de transacao para exportacaol em formato excel, sem paginacao
     * @param filtro Os filtros informados
     * @return A lista de resultados
     */
    List<ExtratoPedidoTransacao> pesquisarParaExportacao(FiltroPesquisaExtratoPedidoCreditoVo filtro);

}
