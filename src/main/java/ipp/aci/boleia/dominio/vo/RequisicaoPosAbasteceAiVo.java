package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Representa a estrutura da requisicao feita ao servico de conciliacao do POS/Abastece Aí
 */
public class RequisicaoPosAbasteceAiVo {

    private Long nsuZacReqOriginal;

    private String status;

    private Integer statusCode;

    private List<RequisicaoAssociadaPosAbasteceAiVo> requisicoes;

    public RequisicaoPosAbasteceAiVo() {
        //construtor default
    }

    public Long getNsuZacReqOriginal() {
        return nsuZacReqOriginal;
    }

    public void setNsuZacReqOriginal(Long nsuZacReqOriginal) {
        this.nsuZacReqOriginal = nsuZacReqOriginal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<RequisicaoAssociadaPosAbasteceAiVo> getRequisicoes() {
        return requisicoes;
    }

    public void setRequisicoes(List<RequisicaoAssociadaPosAbasteceAiVo> requisicoes) {
        this.requisicoes = requisicoes;
    }
}
