package ipp.aci.boleia.dominio.vo.salesforce;

import java.util.Date;

/**
 * Filtro de pesquisa da consulta de chamados.
 */
public class FiltroConsultaChamadosVo {
    private String numeroChamado;
    private Date dataAbertura;
    private String status;

    public String getNumeroChamado() {
        return numeroChamado;
    }

    public void setNumeroChamado(String numeroChamado) {
        this.numeroChamado = numeroChamado;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
