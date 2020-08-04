package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.excecao.ExcecaoServicoIndisponivel;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;

/**
 * Responsável pela integração com o agenciador de frete
 */
public interface IAgenciadorFreteExternoDados {

    /**
     * Verificar se o fornecedor tem saldo disponivel através do pedido
     * @param transacao A Tramsação a ser verificada
     * @return True se tem saldo disponivel e false se não tem
     * @throws ExcecaoValidacao Caso os dados de entrada estejam inconsistentes
     * @throws ExcecaoServicoIndisponivel Caso o fornecedor esteja indisponível
     */
    boolean temSaldoDisponivel(Transacao transacao) throws ExcecaoValidacao, ExcecaoServicoIndisponivel;
}
