package ipp.aci.boleia.dominio.vo;

import java.util.Date;

/**
 * Conteudo da resposta com erro ao tentar atualizar exigencia de nota fiscal
 */
public class AtualizarExigenciaNfeErroVo {
    private String cnpj;
    private Boolean exigeNotaFiscal;
    private Integer statusCode;
    private Date data;

    /**
     * construtor padrão
     */
    public AtualizarExigenciaNfeErroVo() {
        //Para serializacao
    }

    /**
     * Constroi os dados de resposta de atualizacao de exigencia de nota fiscal quando ocorre erro.
     *
     * @param cnpj da empresa
     * @param exigeNotaFiscal se exige nota fiscal
     * @param data da resposta
     * @param statusCode da resposta de alteracao
     */
    public AtualizarExigenciaNfeErroVo(String cnpj, Boolean exigeNotaFiscal,Date data, Integer statusCode) {
        this.cnpj = cnpj;
        this.exigeNotaFiscal = exigeNotaFiscal;
        this.data = data;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
