package ipp.aci.boleia.dados;


import java.util.List;

import ipp.aci.boleia.dominio.PedidoTag;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPedidoTagVo;

/**
 * Contrato para implementacao de repositorios de entidades PedidoTag
 */
public interface IPedidoTagDados extends IRepositorioBoleiaDados<PedidoTag> {

    /**
     * Obtem os registros que representam o pedidos de tags
     * @param filtro filtro que representa as configurações para obter somente historicos de interesse.
     * @return ResultadoPaginado de pedido de tags
     */
    ResultadoPaginado<PedidoTag> pesquisar(FiltroPesquisaPedidoTagVo filtro);

    /**
     * Obtem o registro pelo código da cobrança
     * @param idCobranca O código da cobrança
     * @return pedido referente à cobrança
     */
    PedidoTag obterPorCobranca(Long idCobranca);
    
    /**
     * Obtem a lista de pedidos a serem atualizados
     * @return lista de pedidos a serem atualizados
     */
    List<PedidoTag> obterPedidosAtualizacaoRastreio();

}
