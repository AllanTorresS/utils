package ipp.aci.boleia.dominio.vo;

import java.util.Date;

public class IncluirExigenciaNfeRespostaVo {
    private String cnpj;
    private Boolean exigeNotaFiscal;
    private Integer statusCode;
    private Date dataAtual;

    public IncluirExigenciaNfeRespostaVo(String cnpj, Boolean exigeNotaFiscal,Date dataAtual, Integer statusCode) {
        this.cnpj = cnpj;
        this.exigeNotaFiscal = exigeNotaFiscal;
        this.dataAtual = dataAtual;
        this.statusCode = statusCode;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Boolean getExigeNotaFiscal() {
        return exigeNotaFiscal;
    }

    public void setExigeNotaFiscal(Boolean exigeNotaFiscal) {
        this.exigeNotaFiscal = exigeNotaFiscal;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Date getDataAtual() {
        return dataAtual;
    }

    public void setDataAtual(Date dataAtual) {
        this.dataAtual = dataAtual;
    }
}
