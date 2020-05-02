package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.vo.MundipaggPedidoVo;
import ipp.aci.boleia.dominio.vo.MundipaggRespostaPedidoVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Contrato para implementacao de repositórios do Km de Vantagens.
 */
public interface IMundipaggDados {

    /**
     * Gera um pedido de compra de creditos pre-pagos
     *
     * @param pedido Dados do pedido
     * @return resposta do mundipagg com token para checkout
     */
    MundipaggRespostaPedidoVo criarPedido(MundipaggPedidoVo pedido);

    /**
     * Atualiza um pedido de credito frota no banco de acordo com dados obtidos do mundipagg
     * @param pedido a ser atualizado
     * @return pedido atualizado
     * @throws ExcecaoValidacao em caso de erro na integracao
     */
    PedidoCreditoFrota atualizarPedido(PedidoCreditoFrota pedido) throws ExcecaoValidacao;

    /**
     * Cancela um pedido de credito de frota enviado ao mundipagg
     * @param pedido a ser cancelado
     * @return pedido cancelado
     * @throws ExcecaoValidacao em caso de erro na integracao
     */
    PedidoCreditoFrota cancelarPedido(PedidoCreditoFrota pedido) throws ExcecaoValidacao;
}