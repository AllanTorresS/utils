package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import ipp.aci.boleia.util.ConstantesFormatacao;

import java.util.Date;

/**
 * Representa uma requisicao associada a uma transacao de queima
 */
public class RequisicaoAssociadaPosAbasteceAiVo {

    private Long nsuZacc;

    private String statusPOS;

    private Integer statusPOSCode;

    @JsonFormat(pattern = ConstantesFormatacao.FORMATO_ISO_8601_SEM_TIMEZONE)
    private Date dataRequisicao;

    public RequisicaoAssociadaPosAbasteceAiVo() {
        //construtor default
    }

    public Long getNsuZacc() {
        return nsuZacc;
    }

    public void setNsuZacc(Long nsuZacc) {
        this.nsuZacc = nsuZacc;
    }

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public String getStatusPOS() {
        return statusPOS;
    }

    public void setStatusPOS(String statusPOS) {
        this.statusPOS = statusPOS;
    }

    public Integer getStatusPOSCode() {
        return statusPOSCode;
    }

    public void setStatusPOSCode(Integer statusPOSCode) {
        this.statusPOSCode = statusPOSCode;
    }
}
