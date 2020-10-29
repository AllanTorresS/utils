package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPedidoCreditoVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades de um Pedido de Credito.
 */
public interface IPedidoCreditoFrotaDados extends IRepositorioBoleiaDados<PedidoCreditoFrota> {

    /**
     * Obtem todos pedidos de credito abertos
     * @return Pedidos abertos
     */
    List<PedidoCreditoFrota> obterTodosAbertos();

    /**
     * Obtem o pedido de credito aberto mais recente dada a dataAtual
     * @param dataAtual para comparacao com validade
     * @return O pedido aberto mais recente
     */
    PedidoCreditoFrota obterUltimoPedidoAberto(Date dataAtual);

    /**
     * Obtem todos pedidos de credito pendentes apos a data informada
     * @param data para comparacao
     * @return Pedidos pendentes
     */
    List<PedidoCreditoFrota> obterPendentesAposData(Date data);

    /**
     * Obtem os pedidos pagos ainda com saldo para realizar debito de extrato
     * @param idFrota a obter os pedidos
     * @return pedidos com saldo da frota
     */
    List<PedidoCreditoFrota> obterPagosComSaldo(Long idFrota);

    /**
     * Pesquisa pedidos de credito para a tela de consulta
     * @param filtro preenchido pelo usuario
     * @return resultado paginado com os pedidos filtrados
     */
    ResultadoPaginado<PedidoCreditoFrota> pesquisar(FiltroPesquisaPedidoCreditoVo filtro);

    /**
     * Obtem os pedidos pagos expirados para processo negocial de expiracao
     * @return pedidos expirados
     */
    List<PedidoCreditoFrota> obterPagosExpirados();

    /**
     * Obtem os pedidos cujo numero sejam semelhantes ao fornecido
     * @param termoPesquisa O termo de pesquisa digitado pelo usuario
     * @return Uma lista de pedidos
     */
    List<PedidoCreditoFrota> obterParaAutocomplete(String termoPesquisa);

    /**
     * Pesquisa pedidos de credito para exportacao de acordo com o filtro
     * @param filtro O filtro preenchido pelo usuario
     * @return Uma lista de parametros para pesquisa
     */
    List<PedidoCreditoFrota> pesquisarParaExportacao(FiltroPesquisaPedidoCreditoVo filtro);


    /**
     * Obtem todos pedidos de credito pagos pendentes de atualização no JDE, dado a dataAtual
     * @param dataAtual para comparacao com validade
     * @return Pedidos abertos
     */
    List<PedidoCreditoFrota> obterPagosNaoVencidosPendentesJDE(Date dataAtual);
}
