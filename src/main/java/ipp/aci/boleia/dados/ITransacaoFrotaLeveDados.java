package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.TransacaoFrotaLeve;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades TransacaoFrotaLeve
 */
public interface ITransacaoFrotaLeveDados extends IRepositorioBoleiaDados<TransacaoFrotaLeve> {

    /**
     * Recupera transacao referente ao nsuZacc fornecido
     * @param nsuZaccOriginal NSU fornecido pelo POS/Abastece Aí para identificar uma transacao
     * @return A TransacaoFrotaLeve correspondente
     */
    TransacaoFrotaLeve obterUltimaTransacaoQueimaPorNsuZaccOriginal(String nsuZaccOriginal);

    /**
     * Recupera a primeira transacao de queima associada a um pedido
     * @param idPedido Identificador de pedido
     * @return Primeira transacao de queima relativa a um pedido
     */
    TransacaoFrotaLeve obterPrimeiraTransacaoQueimaPorCdPedido(Long idPedido);

    /**
     * Recupera uma transacao pelo nsuZacc
     * @param nsuZacc NSU a ser buscado
     * @return transacao encontrada
     */
    TransacaoFrotaLeve obterPorNsuZacc(String nsuZacc);

    /**
     * Recupera todas as transacoes associadas a um pedido
     * @param idPedido Identificador do pedido
     * @return as transacoes encontradas
     */
    List<TransacaoFrotaLeve> obterTransacoesQueimaPorPedido(Long idPedido);
}
