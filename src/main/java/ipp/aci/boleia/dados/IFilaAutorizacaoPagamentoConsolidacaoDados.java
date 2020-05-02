package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;

/**
 * Contrato para envio de autorização de pagamento para consolidação
 */
public interface IFilaAutorizacaoPagamentoConsolidacaoDados {

    /**
     * Envia a autorização de pagamento para a fila de consolidação
     * @param autorizacaoPagamento Autorização de pagamento que será enfileirada.
     */
    void enviarAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento);

    /**
     * Envia a autorização de pagamento para a fila de erro de validação de processamento de campanhas
     *
     * @param idAutorizacaoPagamento O identificador da autorização de pagamento que será enviada.
     */
    void enviarMensagemErroValidacaoCampanha(Long idAutorizacaoPagamento);

    /**
     * Envia o identificador de uma {@link ipp.aci.boleia.dominio.TransacaoConsolidada} de uma {@link AutorizacaoPagamento}
     * para a fila de processamento
     *
     * @param identificadorCiclo O identificador do ciclo
     */
    void enviarParaFilaConsolidacao(Long identificadorCiclo);
}
