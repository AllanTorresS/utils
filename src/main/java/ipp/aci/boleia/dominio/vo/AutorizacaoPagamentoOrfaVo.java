package ipp.aci.boleia.dominio.vo;

import java.util.Date;

/**
 * Representa classe auxiliar para a rotina de consolidacao de transacao
 */
public class AutorizacaoPagamentoOrfaVo {

    private Date dataProcessamento;
    private Long idFrota;
    private Long idPontoVenda;

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Long getIdPontoVenda() {
        return idPontoVenda;
    }

    public void setIdPontoVenda(Long idPontoVenda) {
        this.idPontoVenda = idPontoVenda;
    }
}
