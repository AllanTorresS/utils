package ipp.aci.boleia.dados;

/**
 * Contrato para envio de benefícios para a fila de distribuição automática.
 *
 * @author pedro.silva
 */
public interface IFilaDistribuicaoAutomaticaBeneficios {

    /**
     * Envia uma distribuição automática de benefícios para a fila.
     *
     * @param idDistribuicaoAutomatica Identificador da distribuição automática
     */
    void enviarParaDistribuicaoAutomatica(Long idDistribuicaoAutomatica);

    /**
     * Envia 
     */
    void enviarParaFilaErroDistribuicaoAutomatica();
}
