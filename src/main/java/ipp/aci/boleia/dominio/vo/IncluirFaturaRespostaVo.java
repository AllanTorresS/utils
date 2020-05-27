package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.IncluirJDEResp;

import java.math.BigDecimal;

/**
 * Contém dados da resposta retornada pelo serviço de inclusão de cobrança do JDE
 */
public class IncluirFaturaRespostaVo {
    private Long idCobranca;
    private String ciaDocumento;
    private String tipoDocumento;
    private BigDecimal numeroDocumento;
    private BigDecimal qtdeParcelas;
    private Boolean status;
    private String msgErro;

    /**
     * Construtor default
     */
    public IncluirFaturaRespostaVo() {
        // Construtor default
    }

    /**
     * Construtor
     *
     * @param idCobranca o id da cobrança sendo integrada
     * @param resposta a resposta do JDE
     */
    public IncluirFaturaRespostaVo(Long idCobranca, IncluirJDEResp resposta) {
        this.idCobranca = idCobranca;
        this.ciaDocumento = resposta.getCiaDocumento();
        this.tipoDocumento = resposta.getTipoDocumento();
        this.numeroDocumento = resposta.getNumeroDocumento();
        this.qtdeParcelas = resposta.getQtdeParcelas();
        this.status = resposta.isStatus();
        this.msgErro = resposta.getMsgErro();
    }

    public Long getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(Long idCobranca) {
        this.idCobranca = idCobranca;
    }

    public String getCiaDocumento() {
        return ciaDocumento;
    }

    public void setCiaDocumento(String ciaDocumento) {
        this.ciaDocumento = ciaDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public BigDecimal getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(BigDecimal numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public BigDecimal getQtdeParcelas() {
        return qtdeParcelas;
    }

    public void setQtdeParcelas(BigDecimal qtdeParcelas) {
        this.qtdeParcelas = qtdeParcelas;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }
}
