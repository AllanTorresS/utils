package ipp.aci.boleia.dados;

/**
 * Contrato para envio de alteração de status frota
 */
public interface IFilaEmailsAlteracaoStatusFrota {

    /**
     * Envia o email de notificação de alteração de status de uma {@link ipp.aci.boleia.dominio.Frota}
     * para a fila de processamento
     *
     * @param idMotivoAlteracaoStatus O motivo de alteração de status de frota para notificar via email.
     */
    void enviarMotivoParaFila(Long idMotivoAlteracaoStatus);
}
