package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.Pedido;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.excecao.ExcecaoServicoIndisponivel;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

import java.math.BigDecimal;

/**
 * Responsável pela integração com o agenciador de frete
 */
public interface IAgenciadorFreteExternoDados {

    /**
     * Verificar se o fornecedor tem saldo de saque disponivel através da transação
     * @param transacao A Tramsação a ser atualizada
     * @return o saldo do saque disponivel
     * @throws ExcecaoValidacao Caso os dados de entrada estejam inconsistentes
     * @throws ExcecaoServicoIndisponivel Caso o fornecedor esteja indisponível
     */
    BigDecimal obterSaldoDeSaqueDisponivel(Transacao transacao) throws ExcecaoValidacao, ExcecaoServicoIndisponivel;

    /**
     * Confirma a transação no fornecedor
     * @param transacao A Tramsação a ser confirmada
     * @throws ExcecaoValidacao Caso os dados de entrada estejam inconsistentes
     * @throws ExcecaoServicoIndisponivel Caso o fornecedor esteja indisponível
     */
    void confirmarTransacao(Transacao transacao) throws ExcecaoValidacao, ExcecaoServicoIndisponivel;

    /**
     * Cancela a transação no fornecedor
     * @param pedido o pedido a ser cancelado
     * @throws ExcecaoValidacao Caso os dados de entrada estejam inconsistentes
     * @throws ExcecaoServicoIndisponivel Caso o fornecedor esteja indisponível
     */
    void cancelarTransacao(Pedido pedido) throws ExcecaoValidacao, ExcecaoServicoIndisponivel;
}
