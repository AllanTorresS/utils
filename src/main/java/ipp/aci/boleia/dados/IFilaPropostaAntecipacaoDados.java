package ipp.aci.boleia.dados;


/**
 * Contrato para envio de propostas para processamento
 */
public interface IFilaPropostaAntecipacaoDados {

    /**
     * Envia a proposta para a fila de processamento
     *
     * @param idProposta ID da proposta a ser processada
     */
    void enviarPropostaParaFila(Long idProposta);
}
