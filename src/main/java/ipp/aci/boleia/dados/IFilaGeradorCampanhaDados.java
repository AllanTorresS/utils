package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;

/**
 * Contrato para envio de {@link AutorizacaoPagamento} para o Gerador de campanha
 */
public interface IFilaGeradorCampanhaDados {

    /**
     * Envia a autorização de pagamento para a fila do gerador de campanha
     *
     * @param autorizacaoPagamento Autorização de pagamento que será enfileirada.
     */
    void enviarAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento);
}
