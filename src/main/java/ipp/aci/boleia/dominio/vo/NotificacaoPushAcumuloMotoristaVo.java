package ipp.aci.boleia.dominio.vo;

/**
 * Representa os dados básicos de uma notificação de acumulo de KMV a ser disparada no dispositivo motorista
 */
public class NotificacaoPushAcumuloMotoristaVo extends BaseNotificacaoPushVo {

    private NotaMotoristaVo notaMotoristaDTO;

    public NotaMotoristaVo getNotaMotoristaDTO() {
        return notaMotoristaDTO;
    }

    public void setNotaMotoristaDTO(NotaMotoristaVo notaMotoristaDTO) {
        this.notaMotoristaDTO = notaMotoristaDTO;
    }
}
