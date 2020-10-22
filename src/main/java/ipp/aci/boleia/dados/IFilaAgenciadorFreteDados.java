package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;

/**
 * Contrato para envio de transações para consolidação
 */
public interface IFilaAgenciadorFreteDados {

    /**
     * Envia a {@link ipp.aci.boleia.dominio.agenciadorfrete.Transacao} para a fila de processamento
     *
     * @param transacao A transacao a ser processada
     */
    void enviarTransacaoParaFilaDeConsolidacao(Transacao transacao);
}
