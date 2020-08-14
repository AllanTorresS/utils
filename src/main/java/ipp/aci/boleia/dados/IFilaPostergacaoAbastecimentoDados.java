package ipp.aci.boleia.dados;

/**
 * Contrato para envio de abastecimentos para a fila de postergação.
 */
public interface IFilaPostergacaoAbastecimentoDados {

    /**
     * Envia um abastecimento para a fila responsável por definir
     * para qual ciclo ele será postergado.
     *
     * @param idAbastecimento Identificador do abastecimento.
     */
    void enviarParaPostergarAbastecimentos(Long idAbastecimento);

    /**
     * Envia um consolidado para a fila responsável por realizar o processamento
     * após uma postergação de abastecimento.
     *
     * @param idConsolidado O identificador da transação consolidada.
     */
    void enviarParaProcessarCicloPostergacao(Long idConsolidado);
}
