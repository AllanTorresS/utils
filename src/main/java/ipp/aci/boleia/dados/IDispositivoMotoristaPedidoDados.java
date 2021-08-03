package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.DispositivoMotoristaPedido;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.vo.PedidoStatusAutorizacaoVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades DispositivoMotoristaPedido
 */
public interface IDispositivoMotoristaPedidoDados extends IRepositorioBoleiaDados<DispositivoMotoristaPedido> {
    /**
     * Pesquisa pedidos ativos (Dentro do intervalo de 30 minutos e não desativado) do motorista.
     *
     * @param motorista Identificador do Motorista
     * @return Um pedido
     */
    List<DispositivoMotoristaPedido> pesquisarAtivosPorMotorista(Motorista motorista);

    /**
     * Pesquisa o Pedido ativo (Dentro do intervalo de 30 minutos e não desativado) com o mesmo numero.
     *
     * @param numero Numero do Pedido
     * @return Um pedido
     */
    DispositivoMotoristaPedido pesquisarAtivoPorNumeroPedido(String numero);

    /**
     * Pesquisa os ultimos combustiveis distintos os quais foram realizados pedidos de abastecimento
     * pelo aplicativo motorista, ordenados pelos pedidos mais recentes
     *
     * @param idMotorista Identificador do Motorista
     * @param idVeiculo Identificador do Veiculo
     * @return Uma lista com os identificadores dos combustiveis
     */
    List<Long> buscarCombustiveisPedidosAnteriores(Long idMotorista, Long idVeiculo);

    /**
     * Pesquisa pedidos do motorista.
     *
     * @param motorista Identificador do Motorista
     * @return A lista de pedido
     */
    List<DispositivoMotoristaPedido> obterPorMotorista(Motorista motorista);

    /**
     * Pesquisa por numero pedido com o status da autorização pagamento onde estão assiciados.
     *
     * @param numero do pedido
     * @return lista com os status da autorização pagamento associadas ao pedido
     */
    List<PedidoStatusAutorizacaoVo> pesquisarStatusPorNumeroPedido(String numero);
}
