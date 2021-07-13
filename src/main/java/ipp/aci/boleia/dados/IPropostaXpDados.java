package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ConfiguracaoAntecipacaoRecebiveis;
import ipp.aci.boleia.dominio.PropostaAntecipacao;
import ipp.aci.boleia.dominio.ReembolsoAntecipado;

/**
 * Interface para manipulação de propostas via API da XP
 */
public interface IPropostaXpDados {

    /**
     * Cria uma proposta de empréstimo com base em uma antecipação
     * @param reembolsoAntecipado o reembolso antecipado
     * @param configuracao a configuração vigente do programa de antecipação
     * @return O id da proposta de empréstimo
     */
    String criarProposta(ReembolsoAntecipado reembolsoAntecipado, ConfiguracaoAntecipacaoRecebiveis configuracao);

    /**
     * Atualiza os dados de uma proposta de crédito
     * @param proposta a proposta a ser atualizada
     */
    void atualizarProposta(PropostaAntecipacao proposta);

    /**
     * Aprova uma proposta de empréstimo
     * @param idProposta o id da proposta
     */
    void aprovarProposta(String idProposta);

    /**
     * Cancela uma proposta de empréstimo
     * @param idProposta o id da proposta
     */
    void cancelarProposta(String idProposta);
}
