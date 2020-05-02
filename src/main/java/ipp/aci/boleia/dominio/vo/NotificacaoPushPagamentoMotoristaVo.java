package ipp.aci.boleia.dominio.vo;

/**
 * Representa os dados básicos de uma notificação de pagamento a ser disparada no dispositivo motorista
 */
public class NotificacaoPushPagamentoMotoristaVo extends BaseNotificacaoPushVo {

    private Long idNota;
    private String nomePontoVenda;
    private Long idAbastecimento;

    public Long getIdNota() {
        return idNota;
    }

    public void setIdNota(Long idNota) {
        this.idNota = idNota;
    }

    public String getNomePontoVenda() {
        return nomePontoVenda;
    }

    public void setNomePontoVenda(String nomePontoVenda) {
        this.nomePontoVenda = nomePontoVenda;
    }

    public Long getIdAbastecimento() {
        return idAbastecimento;
    }

    public void setIdAbastecimento(Long idAbastecimento) {
        this.idAbastecimento = idAbastecimento;
    }

}
