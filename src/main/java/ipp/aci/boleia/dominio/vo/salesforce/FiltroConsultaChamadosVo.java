package ipp.aci.boleia.dominio.vo.salesforce;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Filtro de pesquisa da consulta de chamados.
 */
public class FiltroConsultaChamadosVo extends BaseFiltroPaginado {
    private String numeroChamado;
    private Date dataAbertura;
    private String status;
    private Long idPv;
    private String cnpjContato;
    private String cpfContato;
    private boolean contatoFrota;
    private boolean contatoRevenda;
    private boolean contatoInterno;

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

    public String getCnpjContato() {
        return cnpjContato;
    }

    public void setCnpjContato(String cnpjContato) {
        this.cnpjContato = cnpjContato;
    }

    public String getCpfContato() {
        return cpfContato;
    }

    public void setCpfContato(String cpfContato) {
        this.cpfContato = cpfContato;
    }

    public boolean isContatoFrota() {
        return contatoFrota;
    }

    public void setContatoFrota(boolean contatoFrota) {
        this.contatoFrota = contatoFrota;
    }

    public boolean isContatoRevenda() {
        return contatoRevenda;
    }

    public void setContatoRevenda(boolean contatoRevenda) {
        this.contatoRevenda = contatoRevenda;
    }

    public boolean isContatoInterno() {
        return contatoInterno;
    }

    public void setContatoInterno(boolean contatoInterno) {
        this.contatoInterno = contatoInterno;
    }

    @Transient
    public String getContato() {
        if(cpfContato != null && cnpjContato != null) {
            return cpfContato + cnpjContato;
        }
        return null;
    }
}
